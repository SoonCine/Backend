package com.sparta.miniproject_movie_study_01.jwt;





import com.sparta.miniproject_movie_study_01.controller.request.TokenDto;
import com.sparta.miniproject_movie_study_01.controller.response.ResponseDto;
import com.sparta.miniproject_movie_study_01.domain.Member;
import com.sparta.miniproject_movie_study_01.domain.RefreshToken;
import com.sparta.miniproject_movie_study_01.sercurity.UserDetailsImpl;
import com.sparta.miniproject_movie_study_01.repository.RefreshTokenRepository;
import com.sparta.miniproject_movie_study_01.shared.Authority;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
public class TokenProvider {

  private static final String AUTHORITIES_KEY = "auth";
  private static final String BEARER_PREFIX = "Bearer ";
  private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;            //30분
  private static final long REFRESH_TOKEN_EXPRIRE_TIME = 1000 * 60 * 60 * 24 * 7;     //7일

  private final Key key;

  private final RefreshTokenRepository refreshTokenRepository;
//  private final UserDetailsServiceImpl userDetailsService;

  // JWT 복호화?????
  public TokenProvider(@Value("${jwt.secret}") String secretKey,
      RefreshTokenRepository refreshTokenRepository) {
    this.refreshTokenRepository = refreshTokenRepository;
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  // JWT 토큰 DTO 만들기
  public TokenDto generateTokenDto(Member member) {
    long now = (new Date().getTime());

    Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
    String accessToken = Jwts.builder()
        // 엑세스토큰 안에 집어 넣을 수 있는 거.
        .setSubject(member.getNickname())
        .claim(AUTHORITIES_KEY, Authority.ROLE_MEMBER.toString())
        .setExpiration(accessTokenExpiresIn)
        .signWith(key, SignatureAlgorithm.HS256)
         //jwts 빌더 패턴을 사용하면 .compact();로 끝난다.
        .compact();

    String refreshToken = Jwts.builder()
        .setExpiration(new Date(now + REFRESH_TOKEN_EXPRIRE_TIME))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();

    RefreshToken refreshTokenObject = RefreshToken.builder()
        .id(member.getId())
        .member(member)
        .value(refreshToken)
        .build();

    refreshTokenRepository.save(refreshTokenObject);

    return TokenDto.builder()
        .grantType(BEARER_PREFIX)
        .accessToken(accessToken)
        .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
        .refreshToken(refreshToken)
        .build();

  }

  public Member getMemberFromAuthentication() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || AnonymousAuthenticationToken.class.
        isAssignableFrom(authentication.getClass())) {
      return null;
    }
    return ((UserDetailsImpl) authentication.getPrincipal()).getMember();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (SecurityException | MalformedJwtException e) {
      log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
    } catch (ExpiredJwtException e) {
      log.info("Expired JWT token, 만료된 JWT token 입니다.");
    } catch (UnsupportedJwtException e) {
      log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
    } catch (IllegalArgumentException e) {
      log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
    }
    return false;
  }


  @Transactional(readOnly = true)
  public RefreshToken isPresentRefreshToken(Member member) {
    Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByMember(member);
    return optionalRefreshToken.orElse(null);
  }

  @Transactional
  public ResponseDto<?> deleteRefreshToken(Member member) {
    RefreshToken refreshToken = isPresentRefreshToken(member);
    if (null == refreshToken) {
      return ResponseDto.fail("TOKEN_NOT_FOUND", "존재하지 않는 Token 입니다.");
    }

    refreshTokenRepository.delete(refreshToken);
    return ResponseDto.success("success");
  }
}

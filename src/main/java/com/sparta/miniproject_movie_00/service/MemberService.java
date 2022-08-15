package com.sparta.miniproject_movie_00.service;


import com.sparta.miniproject_movie_00.controller.request.*;
import com.sparta.miniproject_movie_00.controller.response.MemberResponseDto;
import com.sparta.miniproject_movie_00.controller.response.ResponseDto;
import com.sparta.miniproject_movie_00.domain.Member;
import com.sparta.miniproject_movie_00.jwt.TokenProvider;
import com.sparta.miniproject_movie_00.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;
    //  private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    // 이메일 중복확인.
    @Transactional
    public ResponseDto<?> emailCheck(EmailRequestDto email) {

        String emailCheck = email.getEmail();

        if (emailCheck.equals("")) {

            return ResponseDto.success("이메일을 입력해주세요.");

        }
        if (!emailCheck.contains("@")) {
            return ResponseDto.success("이메일 형식이 아닙니다.");
        }

        if (null != isPresentMemberEmail(emailCheck)) { // 이메일 중복이면
            return ResponseDto.success(false);
        } else { // 이메일 중복 아니면
            return ResponseDto.success(true);
        }
    }

    // 닉네임 중복확인.
    @Transactional
    public ResponseDto<?> nickNameCheck(NicknameRequestDto nickname) {

        String nickNameCheck = nickname.getNickname();

        if (nickNameCheck.equals("")) {
            log.info("빈값이다.");
            return ResponseDto.success("닉네임을 입력해주세요");

        } else {
            log.info("빈값이 아니다.");
            if (null != isPresentMember(nickNameCheck)) { // 넥네임 중복이면
                return ResponseDto.success(false);
            } else { // 닉네임 중복 아니면
                return ResponseDto.success(true);
            }

        }



    }


    @Transactional
    public ResponseDto<?> createMember(MemberRequestDto requestDto) {
        if (null != isPresentMember(requestDto.getNickname())) {
//            return ResponseDto.fail("DUPLICATED_NICKNAME",
//                    "중복된 닉네임입니다.");

            return ResponseDto.success(false);
        }

        if (null != isPresentMemberEmail(requestDto.getEmail())) {
//            return ResponseDto.fail("DUPLICATED_EMAIL",
//                    "중복된 이메일입니다.");

            return ResponseDto.success(false);
        }

        if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
//            return ResponseDto.fail("PASSWORDS_NOT_MATCHED",
//                    "비밀번호와 비밀번호 확인이 일치하지 않습니다.");

            return ResponseDto.success(false);
        }

        Member member = Member.builder()
                .email(requestDto.getEmail())
                .nickname(requestDto.getNickname())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .build();
        memberRepository.save(member);
//        return ResponseDto.success(
//                MemberResponseDto.builder()
//                        .id(member.getId())
//                        .nickname(member.getNickname())
//                        .createdAt(member.getCreatedAt())
//                        .modifiedAt(member.getModifiedAt())
//                        .build()
//        );

        return ResponseDto.success(true);


    }

    @Transactional
    public ResponseDto<?> login(LoginRequestDto requestDto, HttpServletResponse response) {
        Member member = isPresentMember(requestDto.getNickname());
        if (null == member) {
//            return ResponseDto.fail("MEMBER_NOT_FOUND",
//                    "사용자를 찾을 수 없습니다.");

            return ResponseDto.success(false);
        }

        if (!member.validatePassword(passwordEncoder, requestDto.getPassword())) {
//            return ResponseDto.fail("INVALID_MEMBER", "사용자를 찾을 수 없습니다.");
            return ResponseDto.success(false);
        }

//    UsernamePasswordAuthenticationToken authenticationToken =
//        new UsernamePasswordAuthenticationToken(requestDto.getNickname(), requestDto.getPassword());
//    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenDto tokenDto = tokenProvider.generateTokenDto(member);
        tokenToHeaders(tokenDto, response);

//        return ResponseDto.success(
//                MemberResponseDto.builder()
//                        .id(member.getId())
//                        .nickname(member.getNickname())
//                        .createdAt(member.getCreatedAt())
//                        .modifiedAt(member.getModifiedAt())
//                        .build()
//        );

        return ResponseDto.success(true);
    }

//  @Transactional
//  public ResponseDto<?> reissue(HttpServletRequest request, HttpServletResponse response) {
//    if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
//      return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
//    }
//    Member member = tokenProvider.getMemberFromAuthentication();
//    if (null == member) {
//      return ResponseDto.fail("MEMBER_NOT_FOUND",
//          "사용자를 찾을 수 없습니다.");
//    }
//
//    Authentication authentication = tokenProvider.getAuthentication(request.getHeader("Access-Token"));
//    RefreshToken refreshToken = tokenProvider.isPresentRefreshToken(member);
//
//    if (!refreshToken.getValue().equals(request.getHeader("Refresh-Token"))) {
//      return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
//    }
//
//    TokenDto tokenDto = tokenProvider.generateTokenDto(member);
//    refreshToken.updateValue(tokenDto.getRefreshToken());
//    tokenToHeaders(tokenDto, response);
//    return ResponseDto.success("success");
//  }

    public ResponseDto<?> logout(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }
        Member member = tokenProvider.getMemberFromAuthentication();
        if (null == member) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "사용자를 찾을 수 없습니다.");
        }

        return tokenProvider.deleteRefreshToken(member);
    }

    @Transactional(readOnly = true)
    public Member isPresentMemberEmail(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        return optionalMember.orElse(null);
    }

    //닉네임 중복 확인
    @Transactional(readOnly = true)
    public Member isPresentMember(String nickname) {
        Optional<Member> optionalMember = memberRepository.findByNickname(nickname);
        return optionalMember.orElse(null);
    }

    public void tokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("Refresh-Token", tokenDto.getRefreshToken());
        response.addHeader("Access-Token-Expire-Time", tokenDto.getAccessTokenExpiresIn().toString());
    }


}

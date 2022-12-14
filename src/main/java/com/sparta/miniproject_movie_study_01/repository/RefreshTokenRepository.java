package com.sparta.miniproject_movie_study_01.repository;




import com.sparta.miniproject_movie_study_01.domain.Member;
import com.sparta.miniproject_movie_study_01.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findByMember(Member member);
}

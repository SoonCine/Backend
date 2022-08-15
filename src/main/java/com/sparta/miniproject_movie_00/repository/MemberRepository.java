package com.sparta.miniproject_movie_00.repository;


import com.sparta.miniproject_movie_00.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<Member> findById(Long id);
  Optional<Member> findByNickname(String nickname);

  Optional<Member> findByEmail(String email);

  boolean existsByNickname(String name);
}

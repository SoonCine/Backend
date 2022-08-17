package com.sparta.miniproject_movie_study_01.controller;


import com.sparta.miniproject_movie_study_01.controller.request.EmailRequestDto;
import com.sparta.miniproject_movie_study_01.controller.request.LoginRequestDto;
import com.sparta.miniproject_movie_study_01.controller.request.MemberRequestDto;
import com.sparta.miniproject_movie_study_01.controller.request.NicknameRequestDto;
import com.sparta.miniproject_movie_study_01.controller.response.ResponseDto;
import com.sparta.miniproject_movie_study_01.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class MemberController {

  private final MemberService memberService;


  @RequestMapping(value = "/api/members/emailcheck", method = RequestMethod.POST)
  public ResponseDto<?> emailCheck(@RequestBody EmailRequestDto emailRequestDto) {
    return memberService.emailCheck(emailRequestDto);
  }

  @RequestMapping(value = "/api/members/nicknamecheck", method = RequestMethod.POST)
  public ResponseDto<?> nickNameCheck(@RequestBody NicknameRequestDto nicknameRequestDto) {
    return memberService.nickNameCheck(nicknameRequestDto);
  }

  // @Valid 있었는데 삭제함.
  @RequestMapping(value = "/api/member/signup", method = RequestMethod.POST)
  public ResponseDto<?> signup(@RequestBody MemberRequestDto requestDto) {
    return memberService.createMember(requestDto);
  }



  @RequestMapping(value = "/api/member/login", method = RequestMethod.POST)
  public ResponseDto<?> login(@RequestBody LoginRequestDto requestDto,
      HttpServletResponse response
  ) {
    return memberService.login(requestDto, response);
  }

//  @RequestMapping(value = "/api/auth/member/reissue", method = RequestMethod.POST)
//  public ResponseDto<?> reissue(HttpServletRequest request, HttpServletResponse response) {
//    return memberService.reissue(request, response);
//  }

  @RequestMapping(value = "/api/auth/member/logout", method = RequestMethod.POST)
  public ResponseDto<?> logout(HttpServletRequest request) {
    return memberService.logout(request);
  }
}

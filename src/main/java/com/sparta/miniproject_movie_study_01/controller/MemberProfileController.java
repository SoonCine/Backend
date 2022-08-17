package com.sparta.miniproject_movie_study_01.controller;


import com.sparta.miniproject_movie_study_01.controller.response.ResponseDto;
import com.sparta.miniproject_movie_study_01.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class MemberProfileController {

    private final MemberService memberService;

    // 프로필 조회.
    @RequestMapping(value = "/api/auth/member/profile", method = RequestMethod.GET)
    public ResponseDto<?> logout(HttpServletRequest request) {
        return memberService.getProfile(request);
    }



//    @RequestMapping(value = "/api/auth/member/profileupdate", method = RequestMethod.PUT)
//    public ResponseDto<?> updateProfile(HttpServletRequest request,
//                                         @RequestBody MemberProfileRequestDto memberProfileRequestDto) throws IOException, IOException, IOException {
//        return memberService.updateProfile(request,memberProfileRequestDto);
//
//    }


    @RequestMapping(value = "/api/auth/member/profileupdateimg", method = RequestMethod.GET)
    public ResponseDto<?> updateProfileimg(HttpServletRequest request,
                                     @RequestParam("images") MultipartFile multipartFile) throws IOException, IOException, IOException {
        return memberService.updateProfileimg(request,multipartFile, "static");

    }






}

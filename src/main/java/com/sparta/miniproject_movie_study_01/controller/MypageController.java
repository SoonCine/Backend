package com.sparta.miniproject_movie_study_01.controller;




import com.sparta.miniproject_movie_study_01.controller.request.MypageRequestDto;
import com.sparta.miniproject_movie_study_01.controller.response.ResponseDto;
import com.sparta.miniproject_movie_study_01.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class MypageController {

    private final MypageService mypageService;

    //accesstoken으로 마이페이지 접근
    @RequestMapping(value = "/api/auth/mypageaccesstoken", method = RequestMethod.GET)
    public ResponseDto<?> mypageAccessToken(HttpServletRequest request) {
        return mypageService.mypageAccessToken(request);
    }

    // /api/post/
    // accesstoken 없이 마이페이지 접근
    @RequestMapping(value = "/api/post/mypagenotaccesstoken", method = RequestMethod.GET)
    public ResponseDto<?> mypagenotaccesstoken(@RequestBody MypageRequestDto mypageRequestDto) {
        return mypageService.mypagenotaccesstoken(mypageRequestDto);
    }


}

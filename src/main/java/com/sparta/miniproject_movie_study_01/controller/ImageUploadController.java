package com.sparta.miniproject_movie_study_01.controller;




import com.sparta.miniproject_movie_study_01.controller.response.ResponseDto;
import com.sparta.miniproject_movie_study_01.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class ImageUploadController {

    private final AwsS3Service awsS3Service;

//    @PostMapping("/images")
//    public String uploadFile(
//            @RequestPart(value = "file") MultipartFile multipartFile) {
//        return awsS3Service.uploadFileV1(multipartFile);
//    }

//    @PostMapping("/images")
//    public String upload(@RequestParam("images") MultipartFile multipartFile) throws IOException, IOException, IOException {
//        return awsS3Service.upload(multipartFile, "static");
//
//    }

    @PostMapping("/images/{id}")
    public ResponseDto<?> uploadPost(@PathVariable Long id,
                                     HttpServletRequest request,
                                     @RequestParam("images") MultipartFile multipartFile) throws IOException, IOException, IOException {
        return awsS3Service.uploadPost(id,request,multipartFile, "static");

    }

    // 여기다가 exceptionHandler 적어도 된다.

}

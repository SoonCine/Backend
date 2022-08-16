package com.sparta.miniproject_movie_study_01.controller;

import com.sparta.miniproject_movie_study_01.controller.request.PostRequestDto;
import com.sparta.miniproject_movie_study_01.controller.response.ResponseDto;
import com.sparta.miniproject_movie_study_01.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class PostController {

  private final PostService postService;

  @RequestMapping(value = "/api/auth/post", method = RequestMethod.POST)
  public ResponseDto<?> createPost(@RequestBody PostRequestDto requestDto,
                                   HttpServletRequest request) {
    return postService.createPost(requestDto, request);
  }

//  @RequestMapping(value = "/api/post/{id}", method = RequestMethod.GET)
//  public ResponseDto<?> getPost(@PathVariable Long id) {
//    return postService.getPost(id);
//  }

  @RequestMapping(value = "/api/post/movieupcomming/{id}", method = RequestMethod.GET)
  public ResponseDto<?> getmovieUpComming(@PathVariable Long id) {
    return postService.getmovieUpComming(id);
  }

//  @RequestMapping(value = "/api/post", method = RequestMethod.GET)
//  public ResponseDto<?> getAllPosts() {
//    return postService.getAllPost();
//  }

  @RequestMapping(value = "/api/post/movieupcomming", method = RequestMethod.GET)
  public ResponseDto<?> getAllmovieUpComming() {
    return postService.getAllmovieUpComming();
  }

  @RequestMapping(value = "/api/auth/post/{id}", method = RequestMethod.PUT)
  public ResponseDto<?> updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto,
      HttpServletRequest request) {
    return postService.updatePost(id, postRequestDto, request);
  }

  @RequestMapping(value = "/api/auth/post/{id}", method = RequestMethod.DELETE)
  public ResponseDto<?> deletePost(@PathVariable Long id,
      HttpServletRequest request) {
    return postService.deletePost(id, request);
  }

}

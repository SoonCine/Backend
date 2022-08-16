package com.sparta.miniproject_movie_study_01.controller;


import com.sparta.miniproject_movie_study_01.controller.request.CommentRequestDto;
import com.sparta.miniproject_movie_study_01.controller.response.ResponseDto;
import com.sparta.miniproject_movie_study_01.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Validated
@RequiredArgsConstructor
@RestController
public class CommentController {

  private final CommentService commentService;
  // 댓글 생성
//  @RequestMapping(value = "/api/auth/comment", method = RequestMethod.POST)
//  public ResponseDto<?> createComment(@RequestBody CommentRequestDto requestDto,
//                                      HttpServletRequest request) {
//    return commentService.createComment(requestDto, request);
//  }
  
  // 상영예정 영화 댓글 생성
  @RequestMapping(value = "/api/auth/movieupcomming/comment", method = RequestMethod.POST)
  public ResponseDto<?> createMovieUpCommingComment(@RequestBody CommentRequestDto requestDto,
                                      HttpServletRequest request) {
    return commentService.createMovieUpCommingComment(requestDto, request);
  }

  // 댓글 전체조회.
  @RequestMapping(value = "/api/comment/{id}", method = RequestMethod.GET)
  public ResponseDto<?> getAllComments(@PathVariable Long id) {
    return commentService.getAllCommentsByPost(id);
  }

//  @RequestMapping(value = "/api/auth/comment/{id}", method = RequestMethod.PUT)
//  public ResponseDto<?> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto,
//      HttpServletRequest request) {
//    return commentService.updateComment(id, requestDto, request);
//  }

  @RequestMapping(value = "/api/auth/movieupcomming/comment/{id}", method = RequestMethod.PUT)
  public ResponseDto<?> updateMovieUpCommingComment(@PathVariable Long id,
                                                    @RequestBody CommentRequestDto requestDto,
                                                    HttpServletRequest request) {
    return commentService.updateMovieUpCommingComment(id, requestDto, request);
  }

//  @RequestMapping(value = "/api/auth/comment/{id}", method = RequestMethod.DELETE)
//  public ResponseDto<?> deleteComment(@PathVariable Long id,
//      HttpServletRequest request) {
//    return commentService.deleteComment(id, request);
//  }

  @RequestMapping(value = "/api/auth/movieupcomming/comment/{id}", method = RequestMethod.DELETE)
  public ResponseDto<?> deleteMovieUpCommingComment(@PathVariable Long id,
                                      HttpServletRequest request) {
    return commentService.deleteMovieUpCommingComment(id, request);
  }


}

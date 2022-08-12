package com.sparta.miniproject_movie_00.controller;


import com.sparta.miniproject_movie_00.controller.request.CommentReplyRequestDto;
import com.sparta.miniproject_movie_00.controller.response.ResponseDto;
import com.sparta.miniproject_movie_00.service.CommentReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CommentReplyControlller {
    // 여기서는 대댓글 추가 수정 삭제만.

    private final CommentReplyService commentReplyService;

    @Autowired
    public CommentReplyControlller(CommentReplyService commentReplyService){

        this.commentReplyService = commentReplyService;

    }
    // 대댓글 작성
    @RequestMapping(value = "/api/auth/commentreply", method = RequestMethod.POST)
    public ResponseDto<?> createCommentReply(@RequestBody CommentReplyRequestDto requestDto,
                                             HttpServletRequest request) {
        return commentReplyService.createCommentReply(requestDto, request);
    }

    // 대댓글 수정
    @RequestMapping(value = "/api/auth/commentreplyupdate/{id}", method = RequestMethod.PUT)
    public ResponseDto<?> updateCommentReply(@PathVariable Long id,
                                        @RequestBody CommentReplyRequestDto requestDto,
                                        HttpServletRequest request) {
        return commentReplyService.updateCommentReply(id, requestDto, request);
    }

    // 대댓글 삭제
    @RequestMapping(value = "/api/auth/commentreplydelete/{id}", method = RequestMethod.DELETE)
    public ResponseDto<?> deleteCommentReply(@PathVariable Long id,
                                        HttpServletRequest request) {
        return commentReplyService.deleteCommentReply(id, request);
    }



}

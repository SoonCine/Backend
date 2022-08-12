package com.sparta.miniproject_movie_00.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentReplyRequestDto {

    private Long commentId;
    private String content;

}

package com.sparta.miniproject_movie_00.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentReplyResponseDto {

    private Long id;
    private Long commentId;
    private String author;
    private String content;
    private Integer likes;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}

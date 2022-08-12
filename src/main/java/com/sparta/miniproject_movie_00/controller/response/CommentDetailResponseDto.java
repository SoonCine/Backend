package com.sparta.miniproject_movie_00.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDetailResponseDto {


    List<CommentResponseDto> commentResponseDtoList;




}

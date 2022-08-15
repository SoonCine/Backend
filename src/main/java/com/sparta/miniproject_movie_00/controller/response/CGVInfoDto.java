package com.sparta.miniproject_movie_00.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CGVInfoDto {

    @JsonIgnore
    String rank;
    String img;
    String movieAge;
    String movieTitle;
    String movieRate; //예매율
    String movieOpenDate; //개봉일
    String like;
//    int seq;

    public CGVInfoDto() {
        // TODO Auto-generated constructor stub
    }



//    public CGVInfoDto(String rank, String img, String movieAge, String movieTitle, String movieRate,
//                      String movieOpenDate, String like, int seq) {
//        super();
//        this.rank = rank;
//        this.img = img;
//        this.movieAge = movieAge;
//        this.movieTitle = movieTitle;
//        this.movieRate = movieRate;
//        this.movieOpenDate = movieOpenDate;
//        this.like = like;
//        this.seq = seq;
//    }

    public CGVInfoDto(String img, String movieAge, String movieTitle, String movieRate,
                      String movieOpenDate, String like) {
        super();
        this.rank = rank;
        this.img = img;
        this.movieAge = movieAge;
        this.movieTitle = movieTitle;
        this.movieRate = movieRate;
        this.movieOpenDate = movieOpenDate;
        this.like = like;
//        this.seq = seq;
    }

    public CGVInfoDto(String img, String movieAge, String movieTitle, String movieRate,
                      String movieOpenDate, String like, int seq) {
        super();
        this.rank = rank;
        this.img = img;
        this.movieAge = movieAge;
        this.movieTitle = movieTitle;
        this.movieRate = movieRate;
        this.movieOpenDate = movieOpenDate;
        this.like = like;
//        this.seq = seq;
    }



    @Override
    public String toString() {
        return "CGVInfoDto [rank=" + rank + ", img=" + img + ", movieAge=" + movieAge + ", movieTitle=" + movieTitle
                + ", movieRate=" + movieRate + ", movieOpenDate=" + movieOpenDate + ", like=" + like
                + "]";
    }


}

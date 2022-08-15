package com.sparta.miniproject_movie_00.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.miniproject_movie_00.controller.request.PostRequestDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert // 디폴트가 null일때 나머지만 insert
@Entity
public class MovieUpComming {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @ColumnDefault("0")
    private String rank;

    @Column
    @ColumnDefault("0")
    private String img;

    @Column
    @ColumnDefault("0")
    private String movieAge;

    @Column
    @ColumnDefault("0")
    private String movieTitle;

    @Column
    @ColumnDefault("0")
    private String movieRate; //예매율

    @Column
    @ColumnDefault("0")
    private String movieOpenDate; //개봉일

    @Column
    @ColumnDefault("0")
    private String like;


    @JoinColumn(name = "post_id")
    @OneToOne
    private Post post;

}

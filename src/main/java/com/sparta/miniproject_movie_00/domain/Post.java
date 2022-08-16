package com.sparta.miniproject_movie_00.domain;


import com.sparta.miniproject_movie_00.controller.request.PostRequestDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert // 디폴트가 null일때 나머지만 insert
@Entity
public class Post extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String title;

  @Column
  private String content;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> comments;

  @Column(name = "likes_count")
  @ColumnDefault("0") //default 0
  private Integer likes_count;
  // 꺼꾸로 되었다. 부모가 자식이 되어버렸네??

  @Column(name = "comment_count")
  @ColumnDefault("0") //default 0
  private Integer comment_count;

  @Column(name = "imgUrl")
  @ColumnDefault("0") //default 0
  private String imgUrl;

  @JoinColumn(name = "movie_info_id")
  @OneToOne
  private MovieUpComming movieUpComming;

  // 원래 FetchType.Lazy
  // 프런트에 안보낼꺼면 @jsonIgnore
  // 보낼꺼면 FetchType.Eager
  @JoinColumn(name = "member_id")
  @ManyToOne(fetch = FetchType.EAGER)
  private Member member;

  public void update(PostRequestDto postRequestDto) {
    this.title = postRequestDto.getTitle();
    this.content = postRequestDto.getContent();
    this.imgUrl = postRequestDto.getImgUrl();
  }
  // 좋아요 갯수 업데이트
  public void updatelike_count(Integer postlike_count){
    this.likes_count = postlike_count;

  }

  // 댓글 갯수 업데이트
  public void updatecomment_count(Integer comment_count){
    this.comment_count = comment_count;

  }

  public boolean validateMember(Member member) {
    return !this.member.equals(member);
  }

}

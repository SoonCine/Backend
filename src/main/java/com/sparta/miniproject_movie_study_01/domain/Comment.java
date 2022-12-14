package com.sparta.miniproject_movie_study_01.domain;

import com.sparta.miniproject_movie_study_01.controller.request.CommentRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert // 디폴트가 null일때 나머지만 insert
@Entity
public class Comment extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JoinColumn(name = "member_id", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Member member;

  @JoinColumn(name = "post_id")
  @ColumnDefault("0") //default 0
  @ManyToOne(fetch = FetchType.LAZY)
  private Post post;

  @JoinColumn(name = "movieUpComming_id")
  @ColumnDefault("0") //default 0
  @ManyToOne(fetch = FetchType.LAZY)
  private MovieUpComming movieUpComming;

  @Column(nullable = false)
  private String content;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CommentReply> commentReplies;

  @Column(name = "likes_count")
  @ColumnDefault("0") //default 0
  private Integer likes_count;


  public void updatelike_count(Integer postlike_count){
    this.likes_count = postlike_count;

  }


  public Comment(List<CommentReply> commentReplyList){

    this.commentReplies = commentReplyList;

  }

  public void update(CommentRequestDto commentRequestDto) {
    this.content = commentRequestDto.getContent();
  }

  public boolean validateMember(Member member) {
    return !this.member.equals(member);
  }
}

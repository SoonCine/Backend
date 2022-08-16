package com.sparta.miniproject_movie_study_01.repository;




import com.sparta.miniproject_movie_study_01.domain.Comment;
import com.sparta.miniproject_movie_study_01.domain.CommentReply;
import com.sparta.miniproject_movie_study_01.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentReplyRepository extends JpaRepository<CommentReply,Long> {

    List<CommentReply> findAllByComment_Id(Long abc);

    List<CommentReply> findAllByPost(Post post);

    List<CommentReply> findAllByComment(Comment comment);
    //List<CommentReply> findAllByMovieUpComming(Comment comment);

    List<CommentReply> findAllByMember_Id(Long member_Id);

    // 이거 좋아요 예시임.
    Long countAllByComment(Long a);
}

package com.sparta.miniproject_movie_00.repository;


import com.sparta.miniproject_movie_00.domain.Comment;
import com.sparta.miniproject_movie_00.domain.CommentReply;
import com.sparta.miniproject_movie_00.domain.Likes;
import com.sparta.miniproject_movie_00.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes,Long> {

    Integer countAllByPost(Post post);
    Integer countAllByComment(Comment comment);
    Integer countAllByCommentReply(CommentReply commentReply);

    void deleteByMember_IdAndPost_Id(Long memberId,Long post_id);
    void deleteByPost(Post post);
    void deleteByMember_IdAndComment_Id(Long memberId,Long comment_id);
    void deleteByMember_IdAndCommentReply_Id(Long memberId,Long commentReply_id);
    boolean existsByMember_IdAndPost_Id(Long member_id,Long post_id);
    boolean existsByMember_IdAndComment_Id(Long member_id,Long comment_id);
    boolean existsByMember_IdAndCommentReply_Id(Long member_id,Long commentReply_id);


}

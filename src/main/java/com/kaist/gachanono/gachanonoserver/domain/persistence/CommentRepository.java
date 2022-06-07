package com.kaist.gachanono.gachanonoserver.domain.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.kaist.gachanono.gachanonoserver.domain.Post.Comment;
import com.kaist.gachanono.gachanonoserver.domain.Post.Posts;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    /* 게시글 댓글 목록 가져오기 */
    List<Comment> getCommentByPostsOrderById(@Param("posts") Posts posts);
}

package com.purna.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.purna.model.CommentReply;

public interface CommentReplyRepository extends JpaRepository<CommentReply, Long> {

	List<CommentReply> findByCommentId(Long commentId);

	List<CommentReply> findByCommentIdAndPostId(Long commentId, Long postId);

}

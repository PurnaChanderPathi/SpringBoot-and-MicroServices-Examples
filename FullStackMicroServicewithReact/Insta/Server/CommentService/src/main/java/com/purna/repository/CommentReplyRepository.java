package com.purna.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.purna.model.CommentReply;

public interface CommentReplyRepository extends JpaRepository<CommentReply, Long> {

	List<CommentReply> findByCommentId(Long commentId);

	List<CommentReply> findByCommentIdAndPostId(Long commentId, Long postId);

	List<CommentReply> findByPostId(Long postId);

	void deleteByCommentId(Long commentId);

	void deleteByPostId(Long commentId);

   Optional<CommentReply> findByCommentReplyId(Long commentReplyId);
}

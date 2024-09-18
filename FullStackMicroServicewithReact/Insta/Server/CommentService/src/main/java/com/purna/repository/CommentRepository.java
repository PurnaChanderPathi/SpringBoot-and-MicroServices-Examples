package com.purna.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.purna.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findByPostId(Long postId);

	Optional<Comment> findByPostIdAndCommentId(Long postId, Long commentId);


	void deleteByPostId(Long postId);

	Optional<Comment> findByCommentId(Long commentId);
}

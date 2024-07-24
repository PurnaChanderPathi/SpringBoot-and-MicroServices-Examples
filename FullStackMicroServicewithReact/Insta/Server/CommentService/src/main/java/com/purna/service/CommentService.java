package com.purna.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.purna.model.Comment;
import com.purna.repository.CommentRepository;

@Service
public class CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	public Comment addComment(Comment comment) {
		return commentRepository.save(comment);
	}
	
	public List<Comment> getCommentsByPostId(Long postId){
		return commentRepository.findByPostId(postId);
	}
	
	public void deleteComment(Long commentId) {
		commentRepository.deleteById(commentId);
	}
	
	public Comment updateComment(Long commentId,Comment comment) {
		Optional<Comment> findById = commentRepository.findById(commentId);
		if(findById.isPresent()) {
			Comment existingComment = findById.get();
			if(comment.getComment() != null && !comment.getComment().isEmpty()) {
				existingComment.setComment(comment.getComment());
			}
			return commentRepository.save(existingComment);
		}else {
			throw new RuntimeException("Comment not found");
		}
	}

}

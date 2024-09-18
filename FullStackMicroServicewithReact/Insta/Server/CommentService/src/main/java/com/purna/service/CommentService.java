package com.purna.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import com.purna.model.Comment;
import com.purna.repository.CommentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	Map<String, Object> map = new HashMap<>();
	
	public Comment addComment(Comment comment) {
		return commentRepository.save(comment);
	}

	public Map<String, Object> getCommentsByPostId(Long postId) {
	Map<String, Object> map = new HashMap<>(); // Initialize the map

	List<Comment> getComments = commentRepository.findByPostId(postId); // Fetch comments

	if (getComments != null && !getComments.isEmpty()) {
		map.put("status", HttpStatus.OK.value()); // Changed to OK (200) for found comments
		map.put("message", "Comments found with postId: " + postId);
		map.put("results", getComments);
	} else {
		map.put("status", HttpStatus.NOT_FOUND.value());
		map.put("message", "Comments not found with postId: " + postId);
	}

	return map;
}

	
	public Map<String, Object> deleteComment(Long commentId) {
		Comment findById = commentRepository.findById(commentId).get();
		if(findById!=null){
			commentRepository.deleteById(commentId);
		}
		String commentReplyUrl = "http://localhost:9197/api/v1/commentsReply/deleteByCommentId/"+commentId;
		Object commentReplyResult = null;
		try {
			commentReplyResult = webClientBuilder.build().delete()
					.uri(commentReplyUrl)
					.retrieve()
					.bodyToMono(Object.class)
					.block();
		}catch (WebClientResponseException e){
			if(e.getCause() instanceof java.net.ConnectException){
				log.error("Error Deleting commentReply: Service is offline", e);
				commentReplyResult = "CommentReply service is offline";
			}else{
				log.error("Error Deleting commentReply with commentId {}",commentId, e);
			}
		}
		map.put("status",HttpStatus.OK.value());
		map.put("message","Comment with given Id: "+commentId+" is deleted");
		map.put("commentReplyResult",commentReplyResult);
		return map;
	}

	public Comment updateComment(Long commentId, Comment comment) {
		Optional<Comment> findById = commentRepository.findById(commentId);
		if(findById!=null && !findById.isEmpty()) {
			Comment existingComment = findById.get();
			if(comment.getComment() != null && !comment.getComment().isEmpty()) {
				existingComment.setComment(comment.getComment());
			}
			return commentRepository.save(existingComment);
		}else {
			throw new RuntimeException("Comment not found");
		}
	}


	public Map<String, Object> getCommentsByPostIdAndCommentId(Long postId, Long commentId) {
		Map<String, Object> map = new HashMap<>(); // Initialize the map

		// Fetch the comment using postId and commentId
		Optional<Comment> getComment = commentRepository.findByPostIdAndCommentId(postId, commentId);

		if (getComment.isEmpty()) {
			// If no comment is found, return a NOT_FOUND response
			map.put("status", HttpStatus.NOT_FOUND.value());
			map.put("message", "Comment not found with postId: " + postId + " and commentId: " + commentId);
		} else {
			// If the comment is found, fetch additional details from another service
			String commentReplyUrl = "http://localhost:9197/api/v1/commentsReply/getCommentByCommentIdAndPostId?commentId=" + commentId + "&postId=" + postId;

			Object commentReplyResult = null;
			try {
				commentReplyResult = webClientBuilder.build().get()
						.uri(commentReplyUrl)
						.retrieve()
						.bodyToMono(Object.class)
						.block();
			} catch (WebClientResponseException e) {
				if (e.getCause() instanceof java.net.ConnectException) {
					log.error("Error fetching commentReply: Service is offline", e);
					commentReplyResult = "CommentReply service is offline";
				} else {
					log.error("Error fetching commentReply commentId {}", commentId, e);
					commentReplyResult = "Error fetching commentReply: " + e.getMessage();
				}
			}

			// Populate the map with the results
			map.put("status", HttpStatus.OK.value());
			map.put("message", "Comment fetched successfully!");
			map.put("comment", getComment.get()); // Use get() to retrieve the Comment object
			map.put("commentReplyResult", commentReplyResult);
		}

		return map;
	}


	@Transactional
	public Map<String, Object> deleteCommentByPostId(Long postId) {
		List<Comment> findByPostId = commentRepository.findByPostId(postId);
		if(findByPostId.isEmpty()){
			map.put("status",HttpStatus.NOT_FOUND.value());
			map.put("message","Comment details not found with given postId: "+postId);
		}else{
			for (Comment deleteComment : findByPostId){
				commentRepository.deleteByPostId(deleteComment.getPostId());
				map.put("status",HttpStatus.OK.value());
				map.put("message","Comment details deleted with given postId: "+postId);
			}
		}
		return map;
	}
}

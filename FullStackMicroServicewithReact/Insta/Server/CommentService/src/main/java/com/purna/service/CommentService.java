package com.purna.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import com.purna.model.Comment;
import com.purna.repository.CommentRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

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
		Map<String, Object> map = new HashMap<>();
		Optional<Comment> findByCommentId = commentRepository.findByCommentId(commentId);

		if (findByCommentId.isPresent()) {
			commentRepository.deleteById(commentId);
			String commentReplyUrl = "http://localhost:9197/api/v1/commentsReply/deleteByCommentId/" + commentId;

			try {
				ResponseEntity<Object> responseEntity = webClientBuilder.build().delete()
						.uri(commentReplyUrl)
						.retrieve()
						.toEntity(Object.class)
						.block();

				if (responseEntity.getStatusCode() == HttpStatus.NO_CONTENT) {
					map.put("status", HttpStatus.OK.value());
					map.put("message", "CommentReply with given CommentId: " + commentId + " is deleted, but no content returned.");
				} else if (responseEntity.getStatusCode() == HttpStatus.OK) {
					map.put("status", HttpStatus.OK.value());
					map.put("message", "CommentReply with given CommentId: " + commentId + " is deleted");
					map.put("commentReplyResult", responseEntity.getBody());
				} else {
					map.put("status", HttpStatus.NOT_FOUND.value());
					map.put("message", "CommentReply with given CommentId: " + commentId + " not found");
				}

			}  catch (WebClientResponseException e) {
				if (e.getCause() instanceof java.net.ConnectException) {
					log.error("Error Deleting commentReply: Service is offline", e);
					map.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
					map.put("message", "CommentReply service is offline");
					return map;
				} else {
					log.error("Error Deleting commentReply with commentId {}", commentId, e);
				}
			}
		} else {
			map.put("status", HttpStatus.NOT_FOUND.value());
			map.put("message", "Comment not found with commentId: " + commentId);
		}

		return map;
	}

//	public Comment updateComment(Long commentId, Comment comment) {
//		Optional<Comment> findById = commentRepository.findById(commentId);
//		if(findById!=null && !findById.isEmpty()) {
//			Comment existingComment = findById.get();
//			if(comment.getComment() != null && !comment.getComment().isEmpty()) {
//				existingComment.setComment(comment.getComment());
//			}
//			return commentRepository.save(existingComment);
//		}else {
//			throw new RuntimeException("Comment not found");
//		}
//	}

	public Map<String, Object> updateComment(Long commentId, Comment comment){
		Map<String,Object> response = new HashMap<>();
		Optional<Comment> findById = commentRepository.findById(commentId);
		if(findById.isPresent() && !findById.isEmpty()){
			Comment existingComment = findById.get();
			if(comment.getComment() != null && !comment.getComment().isEmpty()) {
				existingComment.setComment(comment.getComment());
			}
			commentRepository.save(existingComment);
			response.put("status",HttpStatus.OK.value());
			response.put("message","Comment updated successfully...!");
		}else{
			response.put("status",HttpStatus.NOT_FOUND.value());
			response.put("message","Comment not found with commentId :"+commentId);
		}
		return response;
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
			String commentReplyUrl = "http://localhost:9197/api/v1/commentsReply/getCommentByCommentIdAndPostId?commentId="+commentId+"&postId="+postId;

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

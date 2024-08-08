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
	

	public List<Comment> getCommentsByPostId(Long postId){
		return commentRepository.findByPostId(postId);
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


	public Map<String, Object> getCommentsByPostIdAndCommentId(Long postId, Long commentId) {

		Optional<Comment> getComment = commentRepository.findByPostIdAndCommentId(postId,commentId);
		if(getComment.isEmpty()) {
				 map.put("Status", HttpStatus.NOT_FOUND.value());
				 map.put("message", "Comments not found");
				 return map;
		}
			
			String CommentReplyUrl ="http://localhost:9197/api/v1/commentsReply/getCommentByCommentIdAndPostId?commentId=" + commentId + "&postId=" + postId;
			
			Object CommentReplyResult = null;
			try {
				CommentReplyResult = webClientBuilder.build().get()
						.uri(CommentReplyUrl)
						.retrieve()
						.bodyToMono(Object.class)
						.block();
			} catch (WebClientResponseException e) {
				if(e.getCause() instanceof java.net.ConnectException){
					log.error("Error fetching commentReply: Service is offline", e);
					CommentReplyResult = "CommentReply service is offline";
			}else{
				log.error("Error fetching commentReply commentId {}",commentId, e);
				}
			}	
			map.put("status", HttpStatus.OK.value());
			map.put("messsage", "Comments Fetched Successfully...!");
			map.put("getComment", getComment);
			map.put("CommentReplyResult", CommentReplyResult);
			
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

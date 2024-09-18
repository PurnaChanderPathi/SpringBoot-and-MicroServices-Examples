package com.purna.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.purna.model.CommentReply;
import com.purna.repository.CommentReplyRepository;

@Service
public class CommentReplyService {
	
	@Autowired
	private CommentReplyRepository replyRepository;
	
	Map<String, Object> response = new HashMap<>();
	
	public CommentReply addComment(CommentReply commentReply) {
		return replyRepository.save(commentReply);
	}

	public Map<String,Object> getCommentReplyByCommentId(Long commentId){
		Map<String,Object> response = new HashMap<>();
		List<CommentReply> commentReplys = replyRepository.findByCommentId(commentId);
		if(!commentReplys.isEmpty()){
			response.put("status",HttpStatus.FOUND.value());
			response.put("message","CommentReply with CommentId :"+commentId+" found");
			response.put("result",commentReplys);
		}else{
			response.put("status",HttpStatus.NOT_FOUND.value());
			response.put("message","CommentReply with CommentId :"+commentId+" not found");
		}
		return response;
	}

	public Map<String,Object> getCommentReplyByCommentIdAndPostId(Long commentId, Long postId){
		Map<String,Object> response = new HashMap<>();
		List<CommentReply> getCommentReply = replyRepository.findByCommentIdAndPostId(commentId,postId);
		if(getCommentReply.isEmpty()){
			response.put("status",HttpStatus.NOT_FOUND.value());
			response.put("message","CommentReply with given commentId :"+commentId+" and postId :"+postId+" is not found");
		}else{
			response.put("status",HttpStatus.FOUND.value());
			response.put("message","CommentReply with given commentId :"+commentId+" and postId :"+postId+" is found");
			response.put("result",getCommentReply);
		}
		return response;
	}

	public Map<String, Object> getCommentReplyByPostId(Long postId) {
		Map<String, Object> response = new HashMap<>();
		List<CommentReply> commentsResults = replyRepository.findByPostId(postId);

		if (commentsResults.isEmpty()) {
			response.put("status", HttpStatus.NOT_FOUND.value());
			response.put("message", "No comments found with postId: " + postId);
		} else {
			response.put("status", HttpStatus.OK.value());
			response.put("message", "Comment replies fetched successfully!");
			response.put("commentsResults", commentsResults);
		}

		return response;
	}

	public Map<String,Object> deleteById(Long commentReplyId){
		Optional<CommentReply> findById = replyRepository.findByCommentReplyId(commentReplyId);
		if(findById.isPresent()){
			replyRepository.deleteById(commentReplyId);
			response.put("status",HttpStatus.OK.value());
			response.put("message","CommentReply with given id : "+commentReplyId+" is Deleted...!");
		}else{
			response.put("status",HttpStatus.NOT_FOUND.value());
			response.put("message","CommentReply with given id : "+commentReplyId+ "is Not Found");
		}
		return response;
	}

	@Transactional
	public Map<String,Object> deleteByCommentId(Long commentId){
		List<CommentReply> findById = (List<CommentReply>) replyRepository.findByCommentId(commentId);
		if(!findById.isEmpty() && findById!=null){
			for (CommentReply delComment : findById){
				replyRepository.deleteByCommentId(delComment.getCommentId());
			}
			response.put("status",HttpStatus.OK.value());
			response.put("message","CommentReply with given CommentId: "+commentId+" is Deleted...!");
		}else{
			response.put("status",HttpStatus.NOT_FOUND.value());
			response.put("message","CommentReply with given CommentId :"+commentId+ " is Not Found");
		}
		return response;
	}

	@Transactional
	public Map<String,Object> deleteByPostId(Long postId){
		List<CommentReply> findById =  replyRepository.findByPostId(postId);
		if(!findById.isEmpty()){
			for (CommentReply delComment : findById){
				replyRepository.deleteByPostId(delComment.getPostId());
			}
			response.put("status",HttpStatus.OK.value());
			response.put("message","CommentReply with given postId: "+postId+" is Deleted...!");
		}else{
			response.put("status",HttpStatus.NOT_FOUND.value());
			response.put("message","CommentReply with given postId: "+postId+ " is Not Found");
		}
		return response;
	}


	
//	public CommentReply editCommentReply(Long commentReplyId, CommentReply commentReply) {
//		Optional<CommentReply> findByCommentReplyId = replyRepository.findById(commentReplyId);
//		if(findByCommentReplyId.isPresent()) {
//			CommentReply existingCommentReply = findByCommentReplyId.get();
//			if(commentReply.getComment()!= null && !commentReply.getComment().isEmpty()) {
//				existingCommentReply.setComment(commentReply.getComment());
//			}
//			return replyRepository.save(existingCommentReply);
//		}else {
//			throw new RuntimeException("CommentReply Not Found");
//		}
//	}

	public Map<String,Object> editCommentReply(Long commentReplyId, CommentReply commentReply){
		Map<String,Object> response = new HashMap<>();
		Optional<CommentReply> findByCommentReplyId = replyRepository.findByCommentReplyId(commentReplyId);
		if(findByCommentReplyId.isPresent() && !findByCommentReplyId.isEmpty()){
			CommentReply existingCommentReply = findByCommentReplyId.get();
			if(commentReply.getComment()!= null && !commentReply.getComment().isEmpty()) {
			existingCommentReply.setComment(commentReply.getComment());
			}
			 CommentReply result = replyRepository.save(existingCommentReply);
			response.put("status",HttpStatus.FOUND.value());
			response.put("message","CommentReply updated successfully..!");
			response.put("result",result);
		}else{
			response.put("status",HttpStatus.NOT_FOUND.value());
			response.put("message","CommentReply not found with given commentReplyId : "+commentReplyId);
		}
		return response;
	}
}

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
	
	public List<CommentReply> getCommentReplyByCommentId(Long commentId){
		return replyRepository.findByCommentId(commentId);
	}

	
	public List<CommentReply> getCommentReplyByCommentIdAndPostId(Long commentId, Long postId){
		return replyRepository.findByCommentIdAndPostId(commentId,postId);
	}
		
	public Map<String, Object> getCommentReplyByPostId(Long postId){
		List<CommentReply> CommentsResults = replyRepository.findByPostId(postId);
		if(CommentsResults.isEmpty()) {
			response.put("status", HttpStatus.NOT_FOUND.value());
			response.put("message", "no comments found with postId :"+postId);
		}else {
			response.put("status", HttpStatus.OK.value());
			response.put("message", "CommentReplys Fetched Successfully...!");
			response.put("CommentsResults", CommentsResults);
		}
		return response;
	}
	
	
	//public void deleteById(Long commentReplyId) {
	//	replyRepository.deleteById(commentReplyId);
	//}

	public Map<String,Object> deleteById(Long commentReplyId){
		CommentReply findById = replyRepository.findById(commentReplyId).get();
		if(findById!=null){
			replyRepository.deleteById(commentReplyId);
			response.put("status",HttpStatus.OK.value());
			response.put("message","CommentReply with given id: "+commentReplyId+" is Deleted...!");
		}else{
			response.put("status",HttpStatus.NOT_FOUND.value());
			response.put("message","CommentReply with given id:"+commentReplyId+ "is Not Found");
		}
		return response;
	}

	@Transactional
	public Map<String,Object> deleteByCommentId(Long commentId){
		List<CommentReply> findById = (List<CommentReply>) replyRepository.findByCommentId(commentId);
		if(findById!=null){
			for (CommentReply delComment : findById){
				replyRepository.deleteByCommentId(delComment.getCommentId());
			}
			response.put("status",HttpStatus.OK.value());
			response.put("message","CommentReply with given CommentId: "+commentId+" is Deleted...!");
		}else{
			response.put("status",HttpStatus.NOT_FOUND.value());
			response.put("message","CommentReply with given CommentId:"+commentId+ "is Not Found");
		}
		return response;
	}

	@Transactional
	public Map<String,Object> deleteByPostId(Long postId){
		List<CommentReply> findById =  replyRepository.findByPostId(postId);
		if(findById!=null){
			for (CommentReply delComment : findById){
				replyRepository.deleteByPostId(delComment.getPostId());
			}
			response.put("status",HttpStatus.OK.value());
			response.put("message","CommentReply with given postId: "+postId+" is Deleted...!");
		}else{
			response.put("status",HttpStatus.NOT_FOUND.value());
			response.put("message","CommentReply with given postId:"+postId+ "is Not Found");
		}
		return response;
	}


	
	public CommentReply editCommentReply(Long commentReplyId, CommentReply commentReply) {
		Optional<CommentReply> findByCommentReplyId = replyRepository.findById(commentReplyId);
		if(findByCommentReplyId.isPresent()) {
			CommentReply existingCommentReply = findByCommentReplyId.get();
			if(commentReply.getComment()!= null && !commentReply.getComment().isEmpty()) {
				existingCommentReply.setComment(commentReply.getComment());
			}
			return replyRepository.save(existingCommentReply);
		}else {
			throw new RuntimeException("CommentReply Not Found");
		}
	}

}

package com.purna.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.purna.model.CommentReply;
import com.purna.repository.CommentReplyRepository;

@Service
public class CommentReplyService {
	
	@Autowired
	private CommentReplyRepository replyRepository;
	
	public CommentReply addComment(CommentReply commentReply) {
		return replyRepository.save(commentReply);
	}
	
	public List<CommentReply> getCommentReplyByCommentId(Long commentId){
		return replyRepository.findByCommentId(commentId);
	}
	
	public List<CommentReply> getCommentReplyByCommentIdAndPostId(Long commentId, Long postId){
		return replyRepository.findByCommentIdAndPostId(commentId,postId);
	}
	
	
	public void deleteById(Long commentReplyId) {
		replyRepository.deleteById(commentReplyId);
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

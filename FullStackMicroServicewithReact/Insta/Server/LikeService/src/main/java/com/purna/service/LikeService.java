package com.purna.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.purna.model.Like;
import com.purna.repository.LikeRepository;

@Service
public class LikeService {
	
	@Autowired
	private LikeRepository likeRepository;
	
	
	Map<String,Object> map=new HashMap<>();
	public Map<String,Object> likePost(Long userId, Long postId) {
		Optional<Like> existingLike = likeRepository.findByUserIdAndPostId(userId,postId);
		if(!existingLike.isPresent()) {
			Like like = new Like();
			like.setPostId(postId);
			like.setUserId(userId);
			likeRepository.save(like);
			map.put("status", HttpStatus.CREATED.value());
			map.put("message", "liked Successfully");
		}else {
			likeRepository.delete(existingLike.get());
			map.put("status", HttpStatus.OK.value());
			map.put("message", "deleted Successfully");
		}
		return map;
	}
	
	public void unlikePost(Long userId, Long postId) {
		Optional<Like> existingLike = likeRepository.findByUserIdAndPostId(userId,postId);
		existingLike.ifPresent(likeRepository::delete);
	}

	public Map<String,Object> getLikesByUserIdAndPostId(Long userId, Long postId){
		Optional<Like> like = likeRepository.findByUserIdAndPostId(userId,postId);
		if(like.isPresent()){
			map.put("status",HttpStatus.FOUND.value());
			map.put("message","Fetched successfully");
			map.put("result",like.get());
			return map;
		}else{
			map.put("status", HttpStatus.NOT_FOUND.value());
			map.put("message","Not Found with given userId: "+userId+" and postId: "+postId);
			return map;
		}

	}

}

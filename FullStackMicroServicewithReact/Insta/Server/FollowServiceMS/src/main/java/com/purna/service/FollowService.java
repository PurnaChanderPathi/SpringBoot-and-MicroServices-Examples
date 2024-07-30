package com.purna.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.purna.dto.UserDto;
import com.purna.model.Follow;
import com.purna.repository.FollowRepository;

@Service
public class FollowService {
	 @Autowired
	    private FollowRepository followRepository;
	   
	 @Autowired
	 private WebClient webClient;

	    public Map<String,Object> follerUser(String followerUsername,String followeeUsername){
	        Map<String,Object> response = new HashMap<>();

	        Optional<UserDto> followerOpt = getUserByUsername(followerUsername);
	        Optional<UserDto> followeeOpt = getUserByUsername(followeeUsername);
	        System.out.println("followerOpt"+followerOpt);
	        System.out.println("followeeOpt"+followeeOpt);

	        if(followerOpt.isPresent() && followeeOpt.isPresent()){
	            Long followerId = followerOpt.get().getUserId();
	            Long followeeId = followeeOpt.get().getUserId();
	            System.out.println("followerId"+followerId);
	            System.out.println("followeeId"+followeeId);

	            //check if already following
	            if(followRepository.existsByFollowerIdAndFolloweeId(followerId,followeeId)){
	                response.put("status","error");
	                response.put("message","Already following this user");
	                return response;
	            }
	            Follow saveFollow = new Follow();
	            saveFollow.setFollowerId(followerId);
	            saveFollow.setFolloweeId(followeeId);
	            followRepository.save(saveFollow);

	            response.put("status","success");
	            response.put("message","Successfully followed user");
	        }else{
	            response.put("status","error");
	            response.put("message","User not found");
	        }
	        return response;
	    }

	    public Map<String,Object> unfollowUser(String followerUsername,String followeeUsername){
	        Map<String,Object> response = new HashMap<>();
	        Optional<UserDto> followerOpt = getUserByUsername(followerUsername);
	        Optional<UserDto> followeeOpt = getUserByUsername(followeeUsername);

	        if(followerOpt.isPresent() && followeeOpt.isPresent()){
	            Long followerId = followerOpt.get().getUserId();
	            Long followeeId = followeeOpt.get().getUserId();

	            Optional<Follow> followOpt = followRepository.findByFollowerIdAndFolloweeId(followerId,followeeId);

	            if(followOpt.isPresent()){
	                followRepository.delete(followOpt.get());
	                response.put("status","success");
	                response.put("message","SuccessFully unfollow user");
	            }else{
	                response.put("status","error");
	                response.put("message","Not following this user");
	            }
	        }else{
	            response.put("status","error");
	            response.put("message","User not found");
	        }
	        return response;
	    }

	    private Optional<UserDto> getUserByUsername(String username) {
	    return Optional.ofNullable(webClient
	            .get()
	            .uri("http://localhost:9195/api/v1/users/"+username)
	            .retrieve()
	            .bodyToMono(UserDto.class)
	            .block());
	    }

}

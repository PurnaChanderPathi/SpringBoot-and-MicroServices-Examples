package com.purna.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.purna.dto.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.purna.dto.UserDto;
import com.purna.model.Follow;
import com.purna.repository.FollowRepository;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@Slf4j
public class FollowService {
	@Autowired
	private FollowRepository followRepository;

	@Autowired
	private WebClient webClient;

	public Map<String, Object> follerUser(String followerUsername, String followeeUsername) {
		Map<String, Object> response = new HashMap<>();

		Optional<UserDto> followerOpt = getUserByEmailId(followerUsername);
		Optional<UserDto> followeeOpt = getUserByEmailId(followeeUsername);
		System.out.println("followerOpt" + followerOpt);
		System.out.println("followeeOpt" + followeeOpt);

		if (followerOpt.isPresent() && followeeOpt.isPresent()) {
			Long followerId = followerOpt.get().getUserId();
			Long followeeId = followeeOpt.get().getUserId();
			System.out.println("followerId" + followerId);
			System.out.println("followeeId" + followeeId);

			//check if already following
			if (followRepository.existsByFollowerIdAndFolloweeId(followerId, followeeId)) {
				response.put("status", "error");
				response.put("message", "Already following this user");
				return response;
			}
			Follow saveFollow = new Follow();
			saveFollow.setFollowerId(followerId);
			saveFollow.setFolloweeId(followeeId);
			followRepository.save(saveFollow);

			response.put("status", "success");
			response.put("message", "Successfully followed user");
		} else {
			response.put("status", "error");
			response.put("message", "User not found with given emailIds");
		}
		return response;
	}

	public Map<String, Object> unfollowUser(String followerUsername, String followeeUsername) {
		Map<String, Object> response = new HashMap<>();
		Optional<UserDto> followerOpt = getUserByEmailId(followerUsername);
		Optional<UserDto> followeeOpt = getUserByEmailId(followeeUsername);

		if (followerOpt.isPresent() && followeeOpt.isPresent()) {
			Long followerId = followerOpt.get().getUserId();
			Long followeeId = followeeOpt.get().getUserId();

			Optional<Follow> followOpt = followRepository.findByFollowerIdAndFolloweeId(followerId, followeeId);

			if (followOpt.isPresent()) {
				followRepository.delete(followOpt.get());
				response.put("status", "success");
				response.put("message", "SuccessFully unfollow user");
			} else {
				response.put("status", "error");
				response.put("message", "Not following this user");
			}
		} else {
			response.put("status", "error");
			response.put("message", "User not found with given emailIds");
		}
		return response;
	}




	public Optional<UserDto> getUserByEmailId(String emailId) {
		UserDto userDto = null;
		try {
			userDto = webClient.get()
					.uri("http://localhost:9194/api/v1/users/findByMailId/{emailId}", emailId) // Use URI variable
					.retrieve()
					.bodyToMono(UserDto.class)
					.block();
		} catch (WebClientResponseException e) {
			if (e.getCause() instanceof java.net.ConnectException) {
				log.error("Error Fetching UserDto", e);
				userDto = null;
			} else {
				log.error("Error Fetching UserDto with emailId {}", emailId, e);
			}
		}

		if (userDto == null) {
			log.error("User service is offline or an error occurred");
			return Optional.empty();
		}
		return Optional.of(userDto);
	}


}





package com.purna.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.purna.model.Notification;
import com.purna.repository.NotificationRepository;

@Service
public class NotificationService {
	
	@Autowired
	private NotificationRepository notificationRepository;
	
//	@Autowired
//	private WebClient.Builder webClientBuilder;
	
	public void sendNotification(String recipientUsername,Long recipientId, String message) {
		Notification notification = new Notification();
		notification.setRecipientUsername(recipientUsername);
		notification.setRecipientId(recipientId);
		notification.setMessage(message);
		notification.setTimestamp(LocalDateTime.now());
		notificationRepository.save(notification);
	}
	
	public void notifyUserRegistration(String username, Long userId) {
		String message = "Welcome "+username+ "! thank you for registering";
		sendNotification(username,userId, message);
	}
	
	public void notifyNewPost(String username,Long userId, String postTitle) {
		String message = "New post created by "+username+": "+postTitle;
		sendNotification(username,userId, message);
	}

}

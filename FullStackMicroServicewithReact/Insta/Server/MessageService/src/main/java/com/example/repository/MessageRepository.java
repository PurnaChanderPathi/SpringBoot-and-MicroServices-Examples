package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

	List<Message> findBySenderIdAndReceiverId(String senderId, String receiverId);

	List<Message> findByReceiverId(String receiverId);


	List<Message> findBySenderIdAndReceiverIdOrSenderIdAndReceiverId(String senderId1, String receiverId1, String senderId2, String receiverId2);
}

package com.purna.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.purna.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}

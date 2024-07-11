package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.MailCredentials;


@Repository
public interface MailCredentialsRepository extends JpaRepository<MailCredentials, Long> {

	MailCredentials findByMailId(String mailId);

}

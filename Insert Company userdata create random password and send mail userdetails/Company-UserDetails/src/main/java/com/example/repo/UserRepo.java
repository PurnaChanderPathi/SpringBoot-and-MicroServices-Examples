package com.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.UserDetails;

public interface UserRepo extends JpaRepository<UserDetails, Long> {

}

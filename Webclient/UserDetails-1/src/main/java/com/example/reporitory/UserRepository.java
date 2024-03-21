package com.example.reporitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.UserDetails;

public interface UserRepository extends JpaRepository<UserDetails, Long> {

}

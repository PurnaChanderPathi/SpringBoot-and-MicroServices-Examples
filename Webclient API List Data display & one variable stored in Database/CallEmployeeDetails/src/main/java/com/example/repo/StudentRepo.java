package com.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Students;

public interface StudentRepo extends JpaRepository<Students, Long> {

}

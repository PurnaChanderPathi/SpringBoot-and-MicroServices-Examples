package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Callmessage;

public interface CallmessageRepository extends JpaRepository<Callmessage, Long> {

	public List<Callmessage> findBySystemId(Long id);

}

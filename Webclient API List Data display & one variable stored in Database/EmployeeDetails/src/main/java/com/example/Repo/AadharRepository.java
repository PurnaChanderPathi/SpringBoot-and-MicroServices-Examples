package com.example.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.AadharDetails;

public interface AadharRepository extends JpaRepository<AadharDetails, Long> {

	public Optional<AadharDetails> findByAadharId(Long id);

}

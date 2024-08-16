package com.purna.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.purna.model.CachingEn;

public interface CachingRepo extends JpaRepository<CachingEn, Long> {

	CachingEn findByName(String name);

	CachingEn findByEmail(String email);

	void save(Optional<CachingEn> findById);
}

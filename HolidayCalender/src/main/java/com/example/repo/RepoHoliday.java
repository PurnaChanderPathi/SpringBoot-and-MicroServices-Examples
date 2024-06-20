package com.example.repo;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.EntityHoliday;

public interface RepoHoliday extends JpaRepository<EntityHoliday, Long>{


	@Query("select case when count(h) > 0 then true else false end from EntityHoliday h WHERE h.fullDate = :date")
    boolean isExists(@Param("date") LocalDate date);
	
//    @Query("select case when count(h) > 0 then true else false end from Holiday h where h.fullDate = :fullDate")
//    boolean isFullDateExists(LocalDate fullDate);
}

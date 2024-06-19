package com.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.Holiday;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {
}

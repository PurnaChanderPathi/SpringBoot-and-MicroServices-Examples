package com.purna.repository;


import com.purna.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StoryRepository extends JpaRepository<Story,Long> {


    List<Story> findAllByUserIdAndExpiresAtAfter(Long userId, LocalDateTime now);
}

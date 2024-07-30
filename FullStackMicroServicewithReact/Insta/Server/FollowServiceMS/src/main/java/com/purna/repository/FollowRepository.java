package com.purna.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.purna.model.Follow;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsByFollowerIdAndFolloweeId(Long followerId, Long followeeId);

    Optional<Follow> findByFollowerIdAndFolloweeId(Long followerId, Long followeeId);
}

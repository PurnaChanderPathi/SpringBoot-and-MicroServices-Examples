package com.purna.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.purna.model.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {

	Optional<Like> findByUserIdAndPostId(Long userId, Long postId);

}

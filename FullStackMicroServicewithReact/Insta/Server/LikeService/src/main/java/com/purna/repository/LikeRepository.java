package com.purna.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.purna.model.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {

	Optional<Like> findByUserIdAndPostId(Long userId, Long postId);

    List<Like> findByPostId(Long postId);

    void deleteByPostId(Long postId);
}

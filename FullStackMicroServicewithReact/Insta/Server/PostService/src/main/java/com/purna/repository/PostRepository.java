package com.purna.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.purna.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<List<Post>> findByTitle(String title);
}

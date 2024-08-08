package com.purna.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.purna.model.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByTitle(String title);
}

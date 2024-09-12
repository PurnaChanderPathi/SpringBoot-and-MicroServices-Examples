package com.purna.repositories;

import com.purna.entity.PostDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostDetailsRepository extends JpaRepository<PostDetails,Long> {
}

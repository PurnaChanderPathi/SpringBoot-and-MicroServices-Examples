package com.purna.repository;

import com.purna.entity.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<EmailEntity,Long> {
    boolean existsBySubject(String subject);
}

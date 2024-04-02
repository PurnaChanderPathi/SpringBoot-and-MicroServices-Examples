package com.broadcastMail.repository;

import com.broadcastMail.entites.Login;
import com.broadcastMail.entites.SignUp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignUpRepository extends JpaRepository<SignUp,Long> {
    SignUp findByMailId(String mailId);
}

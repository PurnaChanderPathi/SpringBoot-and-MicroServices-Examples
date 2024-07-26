package com.purna.repository;

import com.purna.model.ForgotPassword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgotPasswordRepoistory extends JpaRepository<ForgotPassword,Long> {

    ForgotPassword findByUsername(String username);
}

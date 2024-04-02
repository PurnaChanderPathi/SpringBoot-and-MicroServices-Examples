package com.broadcastMail.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SignUp {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long signupId;
    private String mailId;
    private String password;
}

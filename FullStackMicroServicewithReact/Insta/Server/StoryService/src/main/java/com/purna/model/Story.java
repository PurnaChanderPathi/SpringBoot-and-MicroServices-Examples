package com.purna.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] image;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
}

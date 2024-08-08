package com.purna.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    private Long postId;
    private String title;
    private String content;
    private String imageUrl; // URL or base64 string
    private Long userId;
}

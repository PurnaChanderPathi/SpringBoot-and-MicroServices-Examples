package com.purna.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String email;

    private String username;

    private String role;
    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] profilePhoto;
}

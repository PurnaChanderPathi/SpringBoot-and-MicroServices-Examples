package com.purna.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long userId;
    private String email;
    private String password;
    private String username;
    private String role;
    private String profilePhoto;
    private boolean accountNonLocked;
    private boolean enabled;
    private boolean credentialsNonExpired;
    private boolean accountNonExpired;

    @JsonProperty("authorities") // Ensure the field maps correctly
    private List<AuthorityDto> authorities;

}

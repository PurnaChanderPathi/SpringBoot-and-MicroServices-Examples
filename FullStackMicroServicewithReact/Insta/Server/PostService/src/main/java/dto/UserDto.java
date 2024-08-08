package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
        private Long userId;
        private String email;
        private String password;
        private String username;
        private String role;
        private byte[] profilePhoto;
}

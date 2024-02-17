package ch.noseryoung.gg.user;

import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String password;
    private String email;
    private String role;

    public UserDto(String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}

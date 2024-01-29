package ch.noseryoung.gg.user;

public class UserDto {
    private String username;
    private String password_hash;
    private String email;
    private String role;

    public UserDto(String username, String password_hash, String email, String role) {
        this.username = username;
        this.password_hash = password_hash;
        this.email = email;
        this.role = role;
    }
}

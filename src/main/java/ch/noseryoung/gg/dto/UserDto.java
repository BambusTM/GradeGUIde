package ch.noseryoung.gg.dto;

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

    // Getters and Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

package com.example.exchangetoys.DTOs;

public class Login {
    private String username;
    private String password;
    private String role;

    public Login(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Override
    public String toString() {
        return "{\nusername: \"" + username + '\"'+ ",\npassword: \"" + password + '\"' +
                ",\n role: \"" + role + "\"}";
    }
}

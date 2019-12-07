package com.example.exchangetoys.DTOs;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Login {
    private String username;
    private String password;
    private String role;

    @Override
    public String toString() {
        return "{\nusername: \"" + username + '\"' + ",\npassword: \"" + password + '\"' +
                ",\n role: \"" + role + "\"}";
    }
}

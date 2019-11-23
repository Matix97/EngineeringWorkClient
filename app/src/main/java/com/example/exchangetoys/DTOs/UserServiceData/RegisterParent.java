package com.example.exchangetoys.DTOs.UserServiceData;

import java.io.Serializable;

public class RegisterParent implements Serializable {
    String name;
    String email;
    String password;
    String verifyPassword;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }

    public RegisterParent(String name, String email, String password, String verifyPassword) {

        this.name = name;
        this.email = email;
        this.password = password;
        this.verifyPassword = verifyPassword;
    }
}

package com.shorty.app.request;

import com.shorty.app.entity.Role;

import javax.validation.constraints.NotNull;

public class UserLoginRequest {

    @NotNull
    private String email;
    @NotNull
    private String password;

    public UserLoginRequest() {
    }

    public UserLoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UserCreationRequest{" + "email='" + email + '\'' + ", password='" + password + '\'' + '}';
    }
}

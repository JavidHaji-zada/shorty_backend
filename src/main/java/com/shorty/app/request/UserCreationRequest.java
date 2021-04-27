package com.shorty.app.request;

import com.shorty.app.entity.Role;

import javax.validation.constraints.NotNull;

public class UserCreationRequest {

    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String name;
    @NotNull
    private String role;

    public UserCreationRequest() {
    }

    public UserCreationRequest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = "USER";
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "UserCreationRequest{" + "email='" + email + '\'' + ", password='" + password + '\'' + '}';
    }
}

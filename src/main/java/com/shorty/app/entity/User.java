package com.shorty.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

@Entity
@Document(collection = "user")
public class User {

    @Id
    @GeneratedValue
    private String id;

    @Column
    @NotEmpty
    @Email
    private String email;

    @Column
    @JsonIgnore
    private String password;

    @Column
    @NotNull
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private String role;

    private Boolean enabled = false;

    private List<Redirect> redirects;

    public User(@NotEmpty @Email String email, String password, @NotNull String name, String role,
            List<Redirect> redirects) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = (role == null || role.isEmpty()) ? "USER" : role;
        this.redirects = redirects;
    }

    public String getId() {
        return id;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Redirect> getRedirects() {
        return redirects;
    }

    public void setRedirects(List<Redirect> redirects) {
        this.redirects = redirects;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", email='" + email + '\'' + ", password='" + password + '\'' + ", name='" + name
                + '\'' + ", redirects='" + redirects + '\'' + ", enabled='" + enabled + '\'' + ", role='" + role + '\''
                + '}';
    }
}

package com.shorty.app.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class ConfirmationToken {

    @Id
    @GeneratedValue
    private String id;

    private String confirmationToken;

    private LocalDate createdAt;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public  ConfirmationToken(User user){
        this.user = user;
        this.createdAt = LocalDate.now();
        this.confirmationToken = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

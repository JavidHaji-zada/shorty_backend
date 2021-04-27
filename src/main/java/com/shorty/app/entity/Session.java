package com.shorty.app.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Document(collection = "session")
public class Session {

    @Id
    @GeneratedValue
    private String id;

    private String sessionID;

    private LocalDate createdAt;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public Session(User user) {
        this.user = user;
        this.createdAt = LocalDate.now();
        this.sessionID = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getSessionID() {
        return sessionID;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }
}

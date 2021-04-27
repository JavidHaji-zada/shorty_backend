package com.shorty.app.repository;

import com.shorty.app.entity.Session;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends MongoRepository<Session, String> {

    Optional<Session> findSessionById(String id);
    Optional<Session> findBySessionID(String sessionID);
}

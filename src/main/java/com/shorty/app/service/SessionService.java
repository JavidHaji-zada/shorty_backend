package com.shorty.app.service;

import com.shorty.app.entity.Session;
import com.shorty.app.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public void saveSession(Session session){
        sessionRepository.save(session);
    }

    public void deleteSession(String id){
        sessionRepository.deleteById(id);
    }

    public Optional<Session> findSessionById(String id) {
        return sessionRepository.findSessionById(id);
    }

    public Optional<Session> findBySessionID(String sessionID){
        return sessionRepository.findBySessionID(sessionID);
    }
}

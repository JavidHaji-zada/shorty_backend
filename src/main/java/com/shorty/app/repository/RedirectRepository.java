package com.shorty.app.repository;

import com.shorty.app.entity.Redirect;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RedirectRepository extends MongoRepository<Redirect, String> {
    Optional<Redirect> findByAlias(String alias);

    Boolean existsByAlias(String alias);
}

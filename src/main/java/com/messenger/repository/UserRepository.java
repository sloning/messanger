package com.messenger.repository;

import com.messenger.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Page<User> findAllByName(String name, Pageable pageable);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}

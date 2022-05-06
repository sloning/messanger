package com.messenger.repository;

import com.messenger.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    public List<User> findAllByName(String name, Pageable pageable);

    public Optional<User> findByEmail(String email);
}

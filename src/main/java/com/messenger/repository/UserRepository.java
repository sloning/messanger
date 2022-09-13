package com.messenger.repository;

import com.messenger.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findAllByName(String name, Pageable pageable);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}

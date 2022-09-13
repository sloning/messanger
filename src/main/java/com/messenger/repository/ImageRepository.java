package com.messenger.repository;

import com.messenger.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByOwnerId(Long ownerId);
}

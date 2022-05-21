package com.messenger.repository;

import com.messenger.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ImageRepository extends MongoRepository<Image, String> {

    Optional<Image> findByOwnerId(String ownerId);
}

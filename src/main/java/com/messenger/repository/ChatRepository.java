package com.messenger.repository;

import com.messenger.model.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRepository extends MongoRepository<Chat, String> {

    List<Chat> findAllByParticipantsContaining(String user);

}

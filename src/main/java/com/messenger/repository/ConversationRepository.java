package com.messenger.repository;

import com.messenger.model.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ConversationRepository extends MongoRepository<Conversation, String> {

    List<Conversation> findAllByUser1(String user1);

    List<Conversation> findAllByUser2(String user2);
}

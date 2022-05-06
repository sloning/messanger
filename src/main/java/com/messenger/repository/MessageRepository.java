package com.messenger.repository;

import com.messenger.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {

    Page<Message> findAllByConversation(String conversation, Pageable pageable);

    List<Message> findAllByTextOrderByDateDesc(String text);

    List<Message> findAllByTextAndSenderOrderByDateDesc(String text, String senderId);
}

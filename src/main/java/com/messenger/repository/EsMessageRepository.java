package com.messenger.repository;

import com.messenger.model.EsMessage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface EsMessageRepository extends ElasticsearchRepository<EsMessage, String> {

    List<EsMessage> findAllByTextAndChatId(String text, String chatId);
}

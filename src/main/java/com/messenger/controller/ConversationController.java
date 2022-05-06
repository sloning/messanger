package com.messenger.controller;

import com.messenger.dto.model.Response;
import com.messenger.model.Conversation;
import com.messenger.service.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/conversation")
public class ConversationController {

    private final ConversationService conversationService;

    @GetMapping
    public Response<List<Conversation>> getConversations() {
        return new Response<>(conversationService.getConversations());
    }

    @PostMapping
    public Response<Conversation> createConversation(@RequestBody Conversation conversation) {
        return new Response<>(conversationService.save(conversation));
    }
}

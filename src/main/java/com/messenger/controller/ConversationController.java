package com.messenger.controller;

import com.messenger.dto.model.ConversationDto;
import com.messenger.dto.model.Response;
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
    public Response<List<ConversationDto>> getConversations() {
        return new Response<>(conversationService.getConversationDtos());
    }

    @PostMapping
    public Response<ConversationDto> createConversation(@RequestBody ConversationDto conversationDto) {
        return new Response<>(conversationService.save(conversationDto));
    }
}

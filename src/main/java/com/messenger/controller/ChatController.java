package com.messenger.controller;

import com.messenger.dto.model.ChatDto;
import com.messenger.dto.model.Response;
import com.messenger.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChatController {

    private final ChatService chatService;

    @GetMapping
    public Response<List<ChatDto>> getChatList() {
        return new Response<>(chatService.getChatDtos());
    }

    @PostMapping
    public Response<ChatDto> createChat(@RequestBody ChatDto chatDto) {
        return new Response<>(chatService.save(chatDto));
    }
}

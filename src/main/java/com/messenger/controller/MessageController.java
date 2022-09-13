package com.messenger.controller;

import com.messenger.dto.model.MessageDto;
import com.messenger.dto.model.Response;
import com.messenger.model.EsMessage;
import com.messenger.model.User;
import com.messenger.service.ChatService;
import com.messenger.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/message")
public class MessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageService messageService;
    private final ChatService chatService;

    @PostMapping
    @Operation(summary = "send message through http")
    public Response<MessageDto> sendMessage(@Valid @RequestBody MessageDto messageDto) {
        return new Response<>(messageService.save(messageDto));
    }

    @GetMapping("/search")
    @Operation(summary = "full-text search through chat by text field")
    public Response<List<EsMessage>> findMessages(@RequestParam String text, @RequestParam Long chat) {
        return new Response<>(messageService.search(text, chat));
    }

    @GetMapping
    @Operation(summary = "get messages by chat")
    public Response<Page<MessageDto>> showChat(@RequestParam Long chat, Pageable pageable) {
        return new Response<>(messageService.findByChat(chat, pageable));
    }

    @DeleteMapping
    @Operation(summary = "delete message by id")
    public Response<Void> deleteMessage(@RequestBody Long id) {
        messageService.delete(id);
        return new Response<>("Message was successfully deleted");
    }

    @MessageMapping("/messages")
    public void exchangeMessages(MessageDto messageDto) {
        messageDto = messageService.save(messageDto);
        List<User> receivers = chatService.getReceivers(messageDto);
        for (User receiver : receivers) {
            simpMessagingTemplate.convertAndSendToUser(receiver.getId().toString(), "/queue/messages", messageDto);
        }
    }
}

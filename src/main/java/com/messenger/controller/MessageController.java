package com.messenger.controller;

import com.messenger.dto.model.MessageDto;
import com.messenger.dto.model.Response;
import com.messenger.model.EsMessage;
import com.messenger.service.ChatService;
import com.messenger.service.MessageService;
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
    public Response<MessageDto> sendMessage(@Valid @RequestBody MessageDto messageDto) {
        return new Response<>(messageService.save(messageDto));
    }

    @GetMapping("/search")
    public Response<List<EsMessage>> findMessages(@RequestParam String text, @RequestParam String chat) {
        return new Response<>(messageService.search(text, chat));
    }

    @GetMapping
    public Response<Page<MessageDto>> showChat(@RequestParam String chat, Pageable pageable) {
        return new Response<>(messageService.findByChat(chat, pageable));
    }

    @DeleteMapping
    public Response<Void> deleteMessage(@RequestBody String id) {
        messageService.delete(id);
        return new Response<>("Message was successfully deleted");
    }

    @MessageMapping("/messages")
    public void exchangeMessages(MessageDto messageDto) {
        messageDto = messageService.save(messageDto);
        List<String> receivers = chatService.getReceivers(messageDto);
        for (String receiver : receivers) {
            simpMessagingTemplate.convertAndSendToUser(receiver, "/queue/messages", messageDto);
        }
    }
}

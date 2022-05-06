package com.messenger.controller;

import com.messenger.dto.model.Response;
import com.messenger.model.EsMessage;
import com.messenger.model.Message;
import com.messenger.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/message")
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public Response<Message> sendMessage(@Valid @RequestBody Message message) {
        return new Response<>(messageService.save(message));
    }

    @GetMapping("/search")
    public Response<List<EsMessage>> findMessages(@RequestParam String text,
                                                  @RequestParam String conversation) {
        return new Response<>(messageService.search(text, conversation));
    }

    @GetMapping
    public Response<Page<Message>> showConversation(@RequestParam String conversation,
                                                    Pageable pageable) {
        return new Response<>(messageService.findByConversation(conversation, pageable));
    }

    @DeleteMapping
    public Response<Void> deleteMessage(@RequestBody String id) {
        messageService.delete(id);
        return new Response<>("Message was successfully deleted");
    }
}

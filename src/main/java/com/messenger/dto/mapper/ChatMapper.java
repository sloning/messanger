package com.messenger.dto.mapper;

import com.messenger.dto.model.ChatDto;
import com.messenger.dto.model.MessageDto;
import com.messenger.dto.model.UserDto;
import com.messenger.model.Chat;
import com.messenger.model.User;
import com.messenger.security.AuthenticationFacade;
import com.messenger.service.MessageService;
import com.messenger.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ChatMapper {

    private final AuthenticationFacade authenticationFacade;
    @Lazy
    private final MessageService messageService;
    private final UserService userService;

    public ChatDto createFrom(Chat chat) {
        ChatDto chatDto = new ChatDto();

        chatDto.setId(chat.getId());
        chatDto.setLastMessage(getLastMessage(chat));
        List<UserDto> userDtoList = determineOtherUsers(chat);
        chatDto.setParticipantDtoList(userDtoList);

        return chatDto;
    }

    private MessageDto getLastMessage(Chat chat) {
        Pageable pageable = PageRequest.of(0, 1, Sort.Direction.DESC, "date");
        Page<MessageDto> messageDtos = messageService.findByChat(chat.getId(), pageable);
        if (messageDtos.hasContent()) {
            return messageDtos.getContent().get(0);
        }
        return null;
    }

    private List<UserDto> determineOtherUsers(Chat chat) {
        Long currentUserId = authenticationFacade.getUserId();
        User currentUser = userService.findById(currentUserId);
        List<UserDto> users = new ArrayList<>();
        for (User participant : chat.getParticipants()) {
            if (!participant.equals(currentUser)) {
                UserDto user = userService.getUserDtoByUser(participant);
                users.add(user);
            }
        }
        return users;
    }

    public Chat createFrom(ChatDto chatDto) {
        Chat chat = new Chat();

        chat.setId(chatDto.getId());
        List<User> users = new ArrayList<>();
        for (Long userId : chatDto.getParticipants()) {
            users.add(userService.findById(userId));
        }
        chat.setParticipants(users);

        return chat;
    }

    public List<ChatDto> createListFrom(List<Chat> chats) {
        List<ChatDto> chatDtos = new ArrayList<>();
        for (Chat chat : chats) {
            chatDtos.add(createFrom(chat));
        }
        return chatDtos;
    }
}

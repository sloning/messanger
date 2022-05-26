package com.messenger.service;

import com.messenger.dto.mapper.ChatMapper;
import com.messenger.dto.model.ChatDto;
import com.messenger.dto.model.MessageDto;
import com.messenger.exception.BadRequestException;
import com.messenger.exception.EntityAlreadyExistsException;
import com.messenger.exception.EntityNotFoundException;
import com.messenger.model.Chat;
import com.messenger.repository.ChatRepository;
import com.messenger.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final AuthenticationFacade authenticationFacade;
    private final ChatMapper chatMapper;

    public List<ChatDto> getChatDtos() {
        return chatMapper.createListFrom(getChatList());
    }

    private List<Chat> getChatList() {
        String userId = authenticationFacade.getUserId();

        return chatRepository.findAllByParticipantsContaining(userId);
    }

    public Chat getChatById(String id) {
        return chatRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Chat was not found")
        );
    }

    public List<String> getReceivers(MessageDto messageDto) {
        Chat chat = getChatById(messageDto.getChatId());
        return chat.getParticipants();
    }

    public boolean isExists(String id) {
        return chatRepository.findById(id).isPresent();
    }

    public boolean isParticipant(Chat chat) {
        String userId = authenticationFacade.getUserId();

        return chat.isUserParticipant(userId);
    }

    public boolean isParticipant(String chatId) {
        Chat chat = getChatById(chatId);
        return isParticipant(chat);
    }

    public ChatDto save(ChatDto chatDto) {
        Chat chat = chatMapper.createFrom(chatDto);
        checkPermission(chat);
        chat = chatRepository.save(chat);
        return chatMapper.createFrom(chat);
    }

    private void checkPermission(Chat chat) {
        if (!isParticipant(chat)) {
            throw new BadRequestException("You can not create new chat for other users");
        }

        checkForDuplicate(chat);
    }

    private void checkForDuplicate(Chat chat) {
        List<Chat> chats = getChatList();
        for (Chat dbChat : chats) {
            if (Objects.equals(dbChat.getFirstUser(), chat.getFirstUser()) &&
                    Objects.equals(dbChat.getSecondUser(), chat.getSecondUser()) ||
                    Objects.equals(dbChat.getFirstUser(), chat.getSecondUser()) &&
                            Objects.equals(dbChat.getSecondUser(), chat.getFirstUser())) {
                throw new EntityAlreadyExistsException("This chat is already exists");
            }
        }
    }
}

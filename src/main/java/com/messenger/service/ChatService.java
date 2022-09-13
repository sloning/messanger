package com.messenger.service;

import com.messenger.dto.mapper.ChatMapper;
import com.messenger.dto.model.ChatDto;
import com.messenger.dto.model.MessageDto;
import com.messenger.exception.BadRequestException;
import com.messenger.exception.EntityAlreadyExistsException;
import com.messenger.exception.EntityNotFoundException;
import com.messenger.model.Chat;
import com.messenger.model.User;
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
    private final UserService userService;

    public List<ChatDto> getChatDtos() {
        return chatMapper.createListFrom(getChatList());
    }

    private List<Chat> getChatList() {
        Long userId = authenticationFacade.getUserId();

        return chatRepository.findAllByParticipantsContaining(userId);
    }

    public Chat getChatById(Long id) {
        return chatRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Chat was not found")
        );
    }

    public List<User> getReceivers(MessageDto messageDto) {
        Chat chat = getChatById(messageDto.getChatId());
        return chat.getParticipants();
    }

    public boolean isExists(Long id) {
        return chatRepository.findById(id).isPresent();
    }

    public boolean isParticipant(Chat chat) {
        Long userId = authenticationFacade.getUserId();
        User user = userService.findById(userId);

        return chat.isUserParticipant(user);
    }

    public boolean isParticipant(Long chatId) {
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

package com.messenger.dto.mapper;

import com.messenger.dto.model.ChatDto;
import com.messenger.dto.model.MessageDto;
import com.messenger.model.Chat;
import com.messenger.model.Image;
import com.messenger.security.AuthenticationFacade;
import com.messenger.service.ImageService;
import com.messenger.service.MessageService;
import com.messenger.util.ImageUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ChatMapper {

    private final AuthenticationFacade authenticationFacade;
    private final MessageService messageService;
    private final ImageService imageService;

    public ChatDto createFrom(Chat chat) {
        ChatDto chatDto = new ChatDto();

        chatDto.setId(chat.getId());
        chatDto.setParticipants(chat.getParticipants());
        chatDto.setLastMessage(getLastMessage(chat));
        chatDto.setImage(getImage(chat));

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

    private byte[] getImage(Chat chat) {
        String userId = authenticationFacade.getUserId();
        if (chat.getParticipants().size() > 2) {
            return null;
        }
        if (chat.getFirstUser().equals(userId)) {
            return getImageBytes(chat.getSecondUser());
        }
        return getImageBytes(chat.getFirstUser());
    }

    private byte[] getImageBytes(String userId) {
        Optional<Image> optionalImage = imageService.findByOwner(userId);
        return optionalImage.map(image -> ImageUtility.decompressImage(image.getImageBytes())).orElse(null);
    }

    public Chat createFrom(ChatDto chatDto) {
        Chat chat = new Chat();

        chat.setId(chatDto.getId());
        chat.setParticipants(chatDto.getParticipants());

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

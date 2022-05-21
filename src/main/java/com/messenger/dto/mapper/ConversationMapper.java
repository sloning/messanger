package com.messenger.dto.mapper;

import com.messenger.dto.model.ConversationDto;
import com.messenger.dto.model.MessageDto;
import com.messenger.model.Conversation;
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
public class ConversationMapper {

    private final AuthenticationFacade authenticationFacade;
    private final MessageService messageService;
    private final ImageService imageService;

    public ConversationDto createFrom(Conversation conversation) {
        ConversationDto conversationDto = new ConversationDto();

        conversationDto.setId(conversation.getId());
        conversationDto.setParticipants(conversation.getParticipants());
        conversationDto.setLastMessage(getLastMessage(conversation));
        conversationDto.setImage(getImage(conversation));

        return conversationDto;
    }

    private MessageDto getLastMessage(Conversation conversation) {
        Pageable pageable = PageRequest.of(0, 1, Sort.Direction.DESC, "date");
        Page<MessageDto> messageDtos = messageService.findByConversation(conversation.getId(), pageable);
        if (messageDtos.hasContent()) {
            return messageDtos.getContent().get(0);
        }
        return null;
    }

    private byte[] getImage(Conversation conversation) {
        String userId = authenticationFacade.getUserId();
        if (conversation.getParticipants().size() > 2) {
            return null;
        }
        if (conversation.getFirstUser().equals(userId)) {
            return getImageBytes(conversation.getSecondUser());
        }
        return getImageBytes(conversation.getFirstUser());
    }

    private byte[] getImageBytes(String userId) {
        Optional<Image> optionalImage = imageService.findByOwner(userId);
        return optionalImage.map(image -> ImageUtility.decompressImage(image.getImageBytes())).orElse(null);
    }

    public Conversation createFrom(ConversationDto conversationDto) {
        Conversation conversation = new Conversation();

        conversation.setId(conversationDto.getId());
        conversation.setParticipants(conversationDto.getParticipants());

        return conversation;
    }

    public List<ConversationDto> createListFrom(List<Conversation> conversations) {
        List<ConversationDto> conversationDtos = new ArrayList<>();
        for (Conversation conversation : conversations) {
            conversationDtos.add(createFrom(conversation));
        }
        return conversationDtos;
    }
}

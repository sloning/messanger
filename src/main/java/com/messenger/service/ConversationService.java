package com.messenger.service;

import com.messenger.dto.mapper.ConversationMapper;
import com.messenger.dto.model.ConversationDto;
import com.messenger.exception.BadRequestException;
import com.messenger.exception.EntityAlreadyExistsException;
import com.messenger.model.Conversation;
import com.messenger.repository.ConversationRepository;
import com.messenger.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final AuthenticationFacade authenticationFacade;
    private final ConversationMapper conversationMapper;

    public List<ConversationDto> getConversationDtos() {
        return conversationMapper.createListFrom(getConversations());
    }

    private List<Conversation> getConversations() {
        String userId = authenticationFacade.getUserId();

        return conversationRepository.findAllByParticipantsContaining(userId);
    }

    public ConversationDto save(ConversationDto conversationDto) {
        Conversation conversation = conversationMapper.createFrom(conversationDto);
        checkPermission(conversation);
        conversation = conversationRepository.save(conversation);
        return conversationMapper.createFrom(conversation);
    }

    private void checkPermission(Conversation conversation) {
        String userId = authenticationFacade.getUserId();

        if (!Objects.equals(userId, conversation.getFirstUser()) &&
                !Objects.equals(userId, conversation.getSecondUser())) {
            throw new BadRequestException("You can not create new conversations for other users");
        }

        List<Conversation> conversations = getConversations();
        for (Conversation dbConversation : conversations) {
            if (Objects.equals(dbConversation.getFirstUser(), conversation.getFirstUser()) &&
                    Objects.equals(dbConversation.getSecondUser(), conversation.getSecondUser()) ||
                    Objects.equals(dbConversation.getFirstUser(), conversation.getSecondUser()) &&
                            Objects.equals(dbConversation.getSecondUser(), conversation.getFirstUser())) {
                throw new EntityAlreadyExistsException("This conversation is already exists");
            }
        }
    }
}

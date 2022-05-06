package com.messenger.service;

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

    public List<Conversation> getConversations() {
        String userId = authenticationFacade.getUserId();

        List<Conversation> conversations = conversationRepository.findAllByUser1(userId);
        conversations.addAll(conversationRepository.findAllByUser2(userId));

        return conversations;
    }

    public Conversation save(Conversation conversation) {
        checkPermission(conversation);
        return conversationRepository.save(conversation);
    }

    private void checkPermission(Conversation conversation) {
        String userId = authenticationFacade.getUserId();

        if (!Objects.equals(userId, conversation.getUser1()) && !Objects.equals(userId, conversation.getUser2())) {
            throw new BadRequestException("You can not create new conversations for other users");
        }

        List<Conversation> conversations = getConversations();
        for (Conversation dbConversation : conversations) {
            if (Objects.equals(dbConversation.getUser1(), conversation.getUser1()) &&
                    Objects.equals(dbConversation.getUser2(), conversation.getUser2()) ||
                    Objects.equals(dbConversation.getUser1(), conversation.getUser2()) &&
                            Objects.equals(dbConversation.getUser2(), conversation.getUser1())) {
                throw new EntityAlreadyExistsException("This conversation is already exists");
            }
        }
    }
}

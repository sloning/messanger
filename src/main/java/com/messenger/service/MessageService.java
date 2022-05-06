package com.messenger.service;

import com.messenger.exception.BadRequestException;
import com.messenger.exception.EntityNotFoundException;
import com.messenger.model.EsMessage;
import com.messenger.model.Message;
import com.messenger.repository.ConversationRepository;
import com.messenger.repository.EsMessageRepository;
import com.messenger.repository.MessageRepository;
import com.messenger.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final AuthenticationFacade authenticationFacade;
    private final ConversationRepository conversationRepository;
    private final EsMessageRepository esMessageRepository;

    public List<EsMessage> search(String text, String conversation) {
        if (text != null && conversation != null) {
            return findByTextAndConversation(text, conversation);
        } else {
            throw new BadRequestException("Parameters can not be null");
        }
    }

    public List<EsMessage> findByTextAndConversation(String text, String conversation) {
        return esMessageRepository.findAllByTextAndConversation(text, conversation);
    }

    public Page<Message> findByConversation(String conversation, Pageable pageable) {
        Pageable queryPageable = getPageableForEachUser(pageable);

        return messageRepository.findAllByConversation(conversation, queryPageable);
    }

    private Pageable getPageableForEachUser(Pageable pageable) {
        int pageNumber = pageable.getPageNumber();
        int pageSize = Math.max(pageable.getPageSize(), 1);
        return PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "date");
    }

    public Message save(Message message) {
        checkPermissionsToSaveMessage(message);
        EsMessage esMessage = new EsMessage(message);
        esMessageRepository.save(esMessage);
        return messageRepository.save(message);
    }

    public void delete(String id) {
        checkPermissionToDeleteMessage(id);
        messageRepository.deleteById(id);
    }

    private void checkPermissionsToSaveMessage(Message message) {
        String userId = authenticationFacade.getUserId();

        if (!Objects.equals(message.getSender(), userId)) {
            throw new BadRequestException("You can not send messages from other users");
        }

        if (message.getConversation() == null || conversationRepository.findById(message.getConversation()).isEmpty()) {
            throw new EntityNotFoundException("This conversation does not exists");
        }
    }

    private void checkPermissionToDeleteMessage(String messageId) {
        String userId = authenticationFacade.getUserId();
        Optional<Message> optionalMessage = messageRepository.findById(messageId);

        if (optionalMessage.isEmpty()) {
            throw new EntityNotFoundException("This message does not exists");
        }

        if (!optionalMessage.get().getSender().equals(userId)) {
            throw new BadRequestException("You can not delete other's messages");
        }
    }
}

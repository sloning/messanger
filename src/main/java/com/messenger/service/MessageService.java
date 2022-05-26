package com.messenger.service;

import com.messenger.dto.mapper.MessageMapper;
import com.messenger.dto.model.MessageDto;
import com.messenger.exception.BadRequestException;
import com.messenger.exception.EntityNotFoundException;
import com.messenger.model.EsMessage;
import com.messenger.model.Message;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final AuthenticationFacade authenticationFacade;
    private final ChatService chatService;
    private final EsMessageRepository esMessageRepository;
    private final MessageMapper messageMapper;

    public List<EsMessage> search(String text, String chat) {
        if (text != null && chat != null) {
            return findByTextAndChat(text, chat);
        } else {
            throw new BadRequestException("Parameters can not be null");
        }
    }

    public List<EsMessage> findByTextAndChat(String text, String chat) {
        return esMessageRepository.findAllByTextAndChatId(text, chat);
    }

    public Page<MessageDto> findByChat(String chat, Pageable pageable) {
        checkIsUserParticipant(chat);
        Pageable queryPageable = getPageableForEachUser(pageable);

        Page<Message> messagesPage = messageRepository.findAllByChatId(chat, queryPageable);
        return messagesPage.map(messageMapper::createFrom);
    }

    private void checkIsUserParticipant(String chat) {
        if (!chatService.isParticipant(chat)) {
            throw new BadRequestException("You are not participant of that chat");
        }
    }

    private Pageable getPageableForEachUser(Pageable pageable) {
        int pageNumber = pageable.getPageNumber();
        int pageSize = Math.max(pageable.getPageSize(), 1);
        return PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "date");
    }

    public MessageDto save(MessageDto messageDto) {
        Message message = messageMapper.createFrom(messageDto);
        message = save(message);
        return messageMapper.createFrom(message);
    }

    private Message save(Message message) {
        checkPermissionsToSaveMessage(message);
        validateMessage(message);
        EsMessage esMessage = new EsMessage(message);
        esMessageRepository.save(esMessage);
        return messageRepository.save(message);
    }

    private void checkPermissionsToSaveMessage(Message message) {
        checkIsUserParticipant(message.getChatId());
        checkIsChatExists(message.getChatId());
    }

    private void checkIsChatExists(String chatId) {
        if (chatId == null || !chatService.isExists(chatId)) {
            throw new EntityNotFoundException("This chat does not exists");
        }
    }

    private void validateMessage(Message message) {
        if (message.getText() == null && message.getImageId() == null) {
            throw new BadRequestException("Message can not be empty!");
        }
    }

    public void delete(String id) {
        checkPermissionToDeleteMessage(id);
        messageRepository.deleteById(id);
    }

    private void checkPermissionToDeleteMessage(String messageId) {
        String userId = authenticationFacade.getUserId();
        Optional<Message> optionalMessage = messageRepository.findById(messageId);

        if (optionalMessage.isEmpty()) {
            throw new EntityNotFoundException("This message does not exists");
        }

        if (!optionalMessage.get().getSenderId().equals(userId)) {
            throw new BadRequestException("You can not delete other's messages");
        }
    }
}

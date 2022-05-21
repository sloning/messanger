package com.messenger.dto.mapper;

import com.messenger.dto.model.MessageDto;
import com.messenger.model.Message;
import com.messenger.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageMapper {

    private final AuthenticationFacade authenticationFacade;

    public MessageDto createFrom(Message message) {
        MessageDto messageDto = new MessageDto();

        messageDto.setId(message.getId());
        messageDto.setText(message.getText());
        messageDto.setDate(message.getDate());
        messageDto.setConversation(message.getConversation());
        messageDto.setRead(message.isRead());
        messageDto.setSender(message.getSender());
        messageDto.setSentByUser(message.getSender().equals(authenticationFacade.getUserId()));
        messageDto.setImageId(message.getImageId());

        return messageDto;
    }

    public Message createFrom(MessageDto messageDto) {
        Message message = new Message();

        message.setConversation(messageDto.getConversation());
        message.setSender(authenticationFacade.getUserId());
        message.setText(messageDto.getText());
        message.setImageId(messageDto.getImageId());
        message.setDate(messageDto.getDate());

        return message;
    }
}

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
        messageDto.setChatId(message.getChatId());
        messageDto.setRead(message.isRead());
        messageDto.setSenderId(message.getSenderId());
        messageDto.setSentByUser(message.getSenderId().equals(authenticationFacade.getUserId()));
        messageDto.setImageId(message.getImageId());

        return messageDto;
    }

    public Message createFrom(MessageDto messageDto) {
        Message message = new Message();

        message.setChatId(messageDto.getChatId());
        message.setSenderId(authenticationFacade.getUserId());
        message.setText(messageDto.getText());
        message.setImageId(messageDto.getImageId());
        message.setDate(messageDto.getDate());

        return message;
    }
}

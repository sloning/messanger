package com.messenger.dto.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ConversationDto {

    private String id;
    @NotNull
    private String user1;
    @NotNull
    private String user2;
    private byte[] image;
    private MessageDto lastMessage;
}

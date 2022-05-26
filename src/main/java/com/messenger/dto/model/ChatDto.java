package com.messenger.dto.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ChatDto {

    private String id;
    @NotNull
    private List<String> participants;
    private List<UserDto> participantDtoList;
    private byte[] image;
    private MessageDto lastMessage;
}

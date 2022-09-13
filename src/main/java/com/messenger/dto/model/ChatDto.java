package com.messenger.dto.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ChatDto {

    private Long id;
    @NotNull
    private List<Long> participants;
    private List<UserDto> participantDtoList;
    private byte[] image;
    private MessageDto lastMessage;
}

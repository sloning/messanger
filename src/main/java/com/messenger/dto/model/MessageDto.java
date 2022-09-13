package com.messenger.dto.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class MessageDto {

    private Long id;
    @NotNull
    private Long chatId;
    @NotNull
    private Long senderId;
    @Length(min = 1, message = "Message must contain at least 1 symbol or image")
    private String text;
    private Long imageId;
    private boolean isSentByUser;
    private Date date = new Date();
    private boolean isRead = false;
}

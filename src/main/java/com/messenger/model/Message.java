package com.messenger.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class Message {

    @Id
    @GeneratedValue
    private Long id;
    private Long chatId;
    private Long senderId;
    private String text;
    private Long imageId;
    private Date date = new Date();
    private boolean isRead = false;
}

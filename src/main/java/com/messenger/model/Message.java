package com.messenger.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class Message {

    @Id
    private String id;
    private String conversation;
    private String sender;
    private String text;
    private String imageId;
    private Date date = new Date();
    private boolean isRead = false;
}

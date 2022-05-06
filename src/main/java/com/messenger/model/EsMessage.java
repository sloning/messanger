package com.messenger.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Document(indexName = "messageindex")
public class EsMessage {

    @Id
    private String id;
    private String conversation;
    private String sender;
    private String receiver;
    private String text;
    private String imageId;
    private Date date = new Date();
    private boolean isRead = false;

    public EsMessage(Message message) {
        id = message.getId();
        conversation = message.getConversation();
        sender = message.getSender();
        receiver = message.getReceiver();
        text = message.getText();
        imageId = message.getImageId();
        date = message.getDate();
        isRead = message.isRead();
    }
}

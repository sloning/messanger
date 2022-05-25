package com.messenger.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Document(indexName = "messageindex")
@Setting(settingPath = "elasticsearch/message-settings.json")
public class EsMessage {

    @Id
    private String id;
    private String chatId;
    private String senderId;
    @Field(type = FieldType.Text, analyzer = "ru_en")
    private String text;
    private String imageId;
    private Date date = new Date();
    private boolean isRead = false;

    public EsMessage(Message message) {
        id = message.getId();
        chatId = message.getChatId();
        senderId = message.getSenderId();
        text = message.getText();
        imageId = message.getImageId();
        date = message.getDate();
        isRead = message.isRead();
    }
}

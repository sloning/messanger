package com.messenger.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Conversation {

    @Id
    private String id;
    private String user1;
    private String user2;
}

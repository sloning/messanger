package com.messenger.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class User {

    @Id
    private String id;
    // TODO unique
    private String username;
    private String password;
    private Long version = 0L;
    private String name;
    private String description;
    private String imageId;
    private String publicKey;
}

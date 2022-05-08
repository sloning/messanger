package com.messenger.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
public class User {

    @Id
    private String id;
    @Indexed(unique = true)
    private String username;
    private String password;
    private Long version = 0L;
    private String name;
    private String description;
    private String imageId;
    private String publicKey;
}

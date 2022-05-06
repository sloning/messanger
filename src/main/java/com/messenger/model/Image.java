package com.messenger.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Image {

    @Id
    private String id;
    private String ownerId;
    private String name;
    private String type;
    private byte[] imageBytes;
}

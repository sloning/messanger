package com.messenger.dto.model;

import lombok.Data;

@Data
public class ImageDto {

    private Long id;
    private Long ownerId;
    private String ownerName;
    private String name;
    private String type;
}

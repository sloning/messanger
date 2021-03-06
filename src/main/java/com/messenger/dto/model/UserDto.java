package com.messenger.dto.model;

import lombok.Data;

@Data
public class UserDto {

    private String id;
    private String username;
    private String name;
    private String description;
    private byte[] image;
}

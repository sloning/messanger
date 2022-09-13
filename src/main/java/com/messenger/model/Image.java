package com.messenger.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Image {

    @Id
    @GeneratedValue
    private Long id;
    private Long ownerId;
    private String name;
    private String type;
    private byte[] imageBytes;
}

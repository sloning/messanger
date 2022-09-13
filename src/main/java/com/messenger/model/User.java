package com.messenger.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "`messenger_user`")
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private Long version = 0L;
    private String name;
    private String description;
    private Long imageId;
}

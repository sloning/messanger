package com.messenger.dto.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class RegisterDto {

    @Length(min = 4, message = "Username must have at least 4 symbols")
    private String username;
    @Length(min = 8, message = "Password must have at least 8 symbols")
    private String password;
    @NotBlank
    private String name;
    private String description;
    private String imageId;
    private String publicKey;
}

package com.messenger.dto.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RegisterDto {

    @NotNull
    @Length(min = 4, message = "Username must have at least 4 symbols")
    private String username;
    @NotNull
    @Length(min = 8, message = "Password must have at least 8 symbols")
    private String password;
    @NotNull
    @NotBlank
    private String name;
    private String description;
    private Long imageId;
    private String publicKey;
}

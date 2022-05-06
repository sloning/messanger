package com.messenger.dto.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class LoginDto {

    @Length(min = 4, message = "Username must have at least 4 symbols")
    private String username;
    @Length(min = 8, message = "Password must have at least 8 symbols")
    private String password;
}

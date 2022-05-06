package com.messenger.dto.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@RequiredArgsConstructor
public class PasswordChangeDto {

    private final String oldPassword;
    @Length(min = 8, message = "Password must have at least 8 symbols")
    private final String newPassword;
}

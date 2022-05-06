package com.messenger.controller;

import com.messenger.dto.model.ErrorResponse;
import com.messenger.exception.BadRequestException;
import com.messenger.exception.EntityAlreadyExistsException;
import com.messenger.exception.EntityNotFoundException;
import com.messenger.exception.JwtAuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponse handleEntityNotFoundException(Exception ex) {
        return ErrorResponse.notFound(ex, 1);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ErrorResponse handleEntityAlreadyExistsException(Exception ex) {
        return ErrorResponse.duplicateEntity(ex, 2);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JwtAuthenticationException.class)
    public ErrorResponse handleJwtAuthenticationException(Exception ex) {
        return ErrorResponse.unauthorized(ex, 3);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ErrorResponse handleBadRequestException(Exception ex) {
        return ErrorResponse.badRequest(ex, 4);
    }
}

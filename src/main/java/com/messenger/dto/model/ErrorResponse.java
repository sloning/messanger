package com.messenger.dto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private Status status;
    private int code;
    private String message;

    public static ErrorResponse badRequest(Exception e, int code) {
        ErrorResponse response = new ErrorResponse();
        response.setStatus(Status.BAD_REQUEST);
        response.setMessage(e.getMessage());
        response.setCode(code);
        return response;
    }

    public static ErrorResponse unauthorized(Exception e, int code) {
        ErrorResponse response = new ErrorResponse();
        response.setStatus(Status.UNAUTHORIZED);
        response.setMessage(e.getMessage());
        response.setCode(code);
        return response;
    }

    public static ErrorResponse validationException(Exception e, int code) {
        ErrorResponse response = new ErrorResponse();
        response.setStatus(Status.VALIDATION_EXCEPTION);
        response.setMessage(e.getMessage());
        response.setCode(code);
        return response;
    }

    public static ErrorResponse wrongCredentials(Exception e, int code) {
        ErrorResponse response = new ErrorResponse();
        response.setStatus(Status.WRONG_CREDENTIALS);
        response.setMessage(e.getMessage());
        response.setCode(code);
        return response;
    }

    public static ErrorResponse accessDenied(Exception e, int code) {
        ErrorResponse response = new ErrorResponse();
        response.setStatus(Status.ACCESS_DENIED);
        response.setMessage(e.getMessage());
        response.setCode(code);
        return response;
    }

    public static ErrorResponse exception(Exception e, int code) {
        ErrorResponse response = new ErrorResponse();
        response.setStatus(Status.EXCEPTION);
        response.setMessage(e.getMessage());
        response.setCode(code);
        return response;
    }

    public static ErrorResponse notFound(Exception e, int code) {
        ErrorResponse response = new ErrorResponse();
        response.setStatus(Status.NOT_FOUND);
        response.setMessage(e.getMessage());
        response.setCode(code);
        return response;
    }

    public static ErrorResponse duplicateEntity(Exception e, int code) {
        ErrorResponse response = new ErrorResponse();
        response.setStatus(Status.DUPLICATE_ENTITY);
        response.setMessage(e.getMessage());
        response.setCode(code);
        return response;
    }
}

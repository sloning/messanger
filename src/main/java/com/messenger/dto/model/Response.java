package com.messenger.dto.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response<T> {

    private static final Status status = Status.OK;
    private String message;
    private T payload;

    public Response(String message) {
        this.message = message;
    }

    public Response(T payload) {
        this.payload = payload;
    }

    public Response(String message, T payload) {
        this.message = message;
        this.payload = payload;
    }
}

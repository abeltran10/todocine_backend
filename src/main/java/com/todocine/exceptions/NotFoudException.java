package com.todocine.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class NotFoudException extends RuntimeException {

    public NotFoudException(String message) {
        super(message);
    }
}

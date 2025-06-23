package com.todocine.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;


public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }


}

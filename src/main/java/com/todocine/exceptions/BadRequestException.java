package com.todocine.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;


public class BadRequestException extends ResponseStatusException {

    public BadRequestException() {
        super(HttpStatus.BAD_REQUEST);

    }

    public BadRequestException(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public BadRequestException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }


}

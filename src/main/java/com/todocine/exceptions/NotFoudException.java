package com.todocine.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class NotFoudException extends ResponseStatusException {
    public NotFoudException(HttpStatusCode status) {
        super(status);
    }

    public NotFoudException(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public NotFoudException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }
}

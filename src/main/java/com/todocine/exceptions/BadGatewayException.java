package com.todocine.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class BadGatewayException extends ResponseStatusException {
    public BadGatewayException(HttpStatusCode status) {
        super(status);
    }

    public BadGatewayException(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public BadGatewayException(String reason) {
        super(HttpStatus.BAD_GATEWAY, reason);
    }
}

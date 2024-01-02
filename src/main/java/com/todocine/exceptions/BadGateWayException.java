package com.todocine.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class BadGateWayException extends Exception {

    public BadGateWayException() {
        super();
    }

    public BadGateWayException(String message) {
        super(message);
    }

    public BadGateWayException(Throwable cause) {
        super(cause);
    }
}

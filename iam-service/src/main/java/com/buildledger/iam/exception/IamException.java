package com.buildledger.iam.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class IamException extends RuntimeException {
    private final HttpStatus status;

    public IamException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}


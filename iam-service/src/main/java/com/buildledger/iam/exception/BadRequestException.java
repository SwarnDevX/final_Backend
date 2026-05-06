package com.buildledger.iam.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends IamException {
    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}


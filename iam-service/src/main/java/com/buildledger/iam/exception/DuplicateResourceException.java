package com.buildledger.iam.exception;

import org.springframework.http.HttpStatus;

public class DuplicateResourceException extends IamException {
    public DuplicateResourceException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}


package com.buildledger.vendor.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class VendorException extends RuntimeException {
    private final HttpStatus status;
    public VendorException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}


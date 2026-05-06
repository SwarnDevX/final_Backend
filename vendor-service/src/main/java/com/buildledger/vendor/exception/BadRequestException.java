package com.buildledger.vendor.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends VendorException {
    public BadRequestException(String message) { super(message, HttpStatus.BAD_REQUEST); }
}


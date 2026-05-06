package com.buildledger.vendor.exception;

import org.springframework.http.HttpStatus;

public class DuplicateResourceException extends VendorException {
    public DuplicateResourceException(String message) { super(message, HttpStatus.CONFLICT); }
}


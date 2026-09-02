package com.intellidine.common.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseApplicationException {
    public BadRequestException(String message) {
        super(message, ErrorCode.INVALID_REQUEST, HttpStatus.BAD_REQUEST);
    }
}

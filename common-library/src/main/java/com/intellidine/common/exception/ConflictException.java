package com.intellidine.common.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends BaseApplicationException {
    public ConflictException(String message) {
        super(message, ErrorCode.CONFLICT_ERROR, HttpStatus.CONFLICT);
    }
}

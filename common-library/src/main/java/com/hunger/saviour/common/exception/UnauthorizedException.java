package com.hunger.saviour.common.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BaseApplicationException {
    public UnauthorizedException(String message) {
        super(message, ErrorCode.AUTHENTICATION_FAILED, HttpStatus.UNAUTHORIZED);
    }
}

package com.hunger.saviour.common.exception;

import org.springframework.http.HttpStatus;

public class ResourceAlreadyExistsException extends BaseApplicationException {
    public ResourceAlreadyExistsException(String message) {
        super(message, ErrorCode.RESOURCE_ALREADY_EXISTS, HttpStatus.CONFLICT);
    }
}

package com.intellidine.common.exception;

import org.springframework.http.HttpStatus;

public class ExternalServiceException extends BaseApplicationException {
    public ExternalServiceException(String message) {
        super(message, ErrorCode.EXTERNAL_SERVICE_ERROR, HttpStatus.BAD_GATEWAY);
    }
}

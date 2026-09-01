package com.hunger.saviour.portal.handlers;

import com.hunger.saviour.portal.dtos.ResponseDTO;
import com.hunger.saviour.portal.exceptions.UnauthorizedException;
import jakarta.ws.rs.core.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApiGatewayHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ResponseDTO> unauthorizedException(UnauthorizedException unauthorizedException){
        ResponseDTO responseDTO = ResponseDTO.builder()
                .errorMessage(unauthorizedException.getMessage())
                .statusCodeDescription(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .timeStamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.UNAUTHORIZED);
    }

}

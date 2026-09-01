package com.hunger.saviour.portal.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDTO {
    private String errorMessage;
    private String statusCodeDescription;
    private LocalDateTime timeStamp;
}

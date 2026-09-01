package com.hunger.saviour.portal.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {
    private List<String> error_messages; // Jakarta validation annotations
    private String exception_message; // Some exceptions occurring in our application, we need to send the exception message
    private String statusCodeDescription;
    private LocalDateTime timestamp;
    private RestaurantDTO restaurant_details;
}

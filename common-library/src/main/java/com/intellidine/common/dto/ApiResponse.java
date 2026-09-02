package com.intellidine.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    @Builder.Default
    private boolean success = true;

    private String message;

    private T data;

    @Builder.Default
    private Instant timestamp = Instant.now();

    private String path;

    private String traceId;

    public static <T> ApiResponse<T> ok(T data, String message, String path, String traceId) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .timestamp(Instant.now())
                .path(path)
                .traceId(traceId)
                .build();
    }

    public static <T> ApiResponse<T> ok(T data, String message) {
        return ok(data, message, null, null);
    }

    public static <T> ApiResponse<T> ok(T data) {
        return ok(data, "Operation successful", null, null);
    }
}

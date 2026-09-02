package com.intellidine.ai.controller;

import com.intellidine.ai.dto.AiChatRequest;
import com.intellidine.ai.dto.AiChatResponse;
import com.intellidine.ai.service.AiAssistantService;
import com.intellidine.common.dto.ApiResponse;
import com.intellidine.common.filter.TraceIdFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ai")
@RequiredArgsConstructor
@Slf4j
public class AiChatController {

    private final AiAssistantService aiAssistantService;

    @PostMapping("/chat")
    public ResponseEntity<ApiResponse<AiChatResponse>> chat(
            @Valid @RequestBody AiChatRequest request,
            HttpServletRequest httpRequest) {
        log.info("Received AI chat request from path: {}", httpRequest.getRequestURI());
        AiChatResponse response = aiAssistantService.processChat(request);
        return ResponseEntity.ok(
                ApiResponse.ok(response, "AI response generated successfully", httpRequest.getRequestURI(), TraceIdFilter.getTraceId())
        );
    }
}

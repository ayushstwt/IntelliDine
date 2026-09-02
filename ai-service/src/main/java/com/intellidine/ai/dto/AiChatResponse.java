package com.intellidine.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiChatResponse {

    private String reply;
    private String conversationId;
    private List<String> sources;
    private List<String> toolsUsed;
    private int tokensUsed;
    @Builder.Default
    private Instant timestamp = Instant.now();
}

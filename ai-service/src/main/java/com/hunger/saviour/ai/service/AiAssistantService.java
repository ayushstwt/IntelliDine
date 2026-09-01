package com.hunger.saviour.ai.service;

import com.hunger.saviour.ai.dto.AiChatRequest;
import com.hunger.saviour.ai.dto.AiChatResponse;
import com.hunger.saviour.ai.rag.RestaurantRagService;
import com.hunger.saviour.ai.tools.RestaurantCatalogTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AiAssistantService {

    private final RestaurantCatalogTool catalogTool;
    private final RestaurantRagService ragService;
    private final com.hunger.saviour.ai.config.AiProperties aiProperties;

    public AiChatResponse processChat(AiChatRequest request) {
        log.info("Processing AI chat request: user='{}', conversationId='{}' using model='{}', endpoint='{}'",
                request.getUserId(), request.getConversationId(), aiProperties.getModel(), aiProperties.getEndpoint());

        String convId = request.getConversationId() != null ? request.getConversationId() : UUID.randomUUID().toString();

        // 1. RAG context retrieval
        List<RestaurantRagService.RagDocument> docs = ragService.retrieveSimilarContext(request.getMessage());
        List<String> sources = docs.stream().map(RestaurantRagService.RagDocument::getId).toList();

        // 2. AI Tool calling execution
        List<RestaurantCatalogTool.RestaurantSearchResult> results = catalogTool.searchRestaurants(request.getMessage());
        List<String> toolsUsed = List.of("RestaurantCatalogTool.searchRestaurants", "RestaurantRagService.retrieveSimilarContext");

        String reply = String.format(
                "Based on IntelliDine's restaurant network, here are the top recommendations for '%s': %s (Rating: %.1f). Key dishes: %s. Delivery Policy: %s",
                request.getMessage(),
                results.get(0).getName(),
                results.get(0).getRating(),
                String.join(", ", results.get(0).getTopDishes()),
                docs.get(0).getContent()
        );

        return AiChatResponse.builder()
                .reply(reply)
                .conversationId(convId)
                .sources(sources)
                .toolsUsed(toolsUsed)
                .tokensUsed(185)
                .timestamp(Instant.now())
                .build();
    }
}

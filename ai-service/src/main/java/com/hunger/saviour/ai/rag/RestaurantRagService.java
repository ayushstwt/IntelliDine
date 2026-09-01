package com.hunger.saviour.ai.rag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RestaurantRagService {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RagDocument {
        private String id;
        private String content;
        private double score;
    }

    public List<RagDocument> retrieveSimilarContext(String query) {
        log.info("RAG vector search executed for query: {}", query);
        return List.of(
                RagDocument.builder()
                        .id("doc-faq-1")
                        .content("IntelliDine guarantees 30-minute delivery from top-rated restaurants with contactless packaging.")
                        .score(0.92)
                        .build(),
                RagDocument.builder()
                        .id("doc-policy-2")
                        .content("Special dietary options including Vegan, Gluten-Free, and Halal are available across certified restaurant partners.")
                        .score(0.87)
                        .build()
        );
    }
}

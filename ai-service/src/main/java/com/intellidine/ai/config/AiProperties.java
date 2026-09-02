package com.intellidine.ai.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "azure.openai")
public class AiProperties {

    private String endpoint;
    private String apiKey;
    private String model;
    private String deploymentName;
    private Double temperature = 0.7;
    private Integer maxTokens = 1000;
    private Long timeoutMs = 30000L;
}

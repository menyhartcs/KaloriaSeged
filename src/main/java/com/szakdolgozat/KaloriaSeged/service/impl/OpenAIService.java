package com.szakdolgozat.KaloriaSeged.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.szakdolgozat.KaloriaSeged.util.OpenAIRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * Implementation for OpenAI Completions API.
 */
@Service
public class OpenAIService {

    @Value("${openai.api.key}")
    private String apiKey;

    private final WebClient webClient;

    // Init the client connection.
    public OpenAIService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com/v1/chat/completions").build();
    }

    // Handles the API call and gets the response.
    public Mono<String> getCompletion(String prompt) {
        OpenAIRequest request = new OpenAIRequest(prompt);

        return this.webClient.post()
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .body(BodyInserters.fromValue(request))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .map(this::extractContentFromResponse)
                .doOnError(WebClientResponseException.class, e -> {
                    System.err.println("Status code: " + e.getStatusCode());
                    System.err.println("Response body: " + e.getResponseBodyAsString());
                })
                .onErrorResume(e -> {
                    e.printStackTrace();
                    return Mono.just("Error: " + e.getMessage());
                });
    }

    // Extracts the message into a simple String from the JSON object.
    private String extractContentFromResponse(String responseJson) {
        String content = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseJson);

            JsonNode choicesNode = rootNode.get("choices");
            if (choicesNode != null && choicesNode.isArray() && !choicesNode.isEmpty()) {
                JsonNode firstChoice = choicesNode.get(0);
                JsonNode messageNode = firstChoice.path("message");
                content = messageNode.path("content").asText();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}

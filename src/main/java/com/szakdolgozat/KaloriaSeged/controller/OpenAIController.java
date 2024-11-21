package com.szakdolgozat.KaloriaSeged.controller;

import com.szakdolgozat.KaloriaSeged.service.impl.OpenAIService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Controller class for the {@link OpenAIService}. Handles the requests coming from client.
 */
@AllArgsConstructor
@CrossOrigin("*")
@RestController
public class OpenAIController {

    private OpenAIService openAIService;

    // Handles the POST request for OpenAI Completion API.
    @PostMapping("/complete")
    public Mono<String> getCompletion(@RequestBody String prompt) {
        return openAIService.getCompletion(prompt);
    }
}

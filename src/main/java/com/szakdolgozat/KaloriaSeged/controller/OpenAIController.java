package com.szakdolgozat.KaloriaSeged.controller;

import com.szakdolgozat.KaloriaSeged.service.impl.OpenAIService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@CrossOrigin("*")
@RestController
public class OpenAIController {

    private OpenAIService openAIService;

    @PostMapping("/complete")
    public Mono<String> getCompletion(@RequestBody String prompt) {
        return openAIService.getCompletion(prompt);
    }
}

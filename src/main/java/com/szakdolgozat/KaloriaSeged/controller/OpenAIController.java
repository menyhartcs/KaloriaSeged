package com.szakdolgozat.KaloriaSeged.controller;

import com.szakdolgozat.KaloriaSeged.service.impl.OpenAIService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@CrossOrigin("*")
@RestController
public class OpenAIController {
    @Autowired
    private OpenAIService openAIService;

    @PostMapping("/complete")
    public Mono<String> getCompletion(@RequestBody String prompt) {
        return openAIService.getCompletion(prompt);
    }
}

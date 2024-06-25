package com.szakdolgozat.KaloriaSeged.service.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpenAIRequest {
    private String model = "gpt-3.5-turbo-16k";
    private OpenAIMessage[] messages;

    public OpenAIRequest(String prompt) {
        this.messages = new OpenAIMessage[] {
                new OpenAIMessage("system", "You are a helpful assistant."),
                new OpenAIMessage("user", prompt)
        };
    }
}

package com.szakdolgozat.KaloriaSeged.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Util class for open ai request.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpenAIRequest {
    private String model = "gpt-4o-mini";
    private OpenAIMessage[] messages;

    public OpenAIRequest(String prompt) {
        this.messages = new OpenAIMessage[] {
                new OpenAIMessage("system", "You are a helpful assistant."),
                new OpenAIMessage("user", prompt)
        };
    }
}

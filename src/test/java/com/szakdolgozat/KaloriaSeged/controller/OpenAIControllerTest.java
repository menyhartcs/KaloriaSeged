package com.szakdolgozat.KaloriaSeged.controller;

import com.szakdolgozat.KaloriaSeged.service.impl.OpenAIService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;

public class OpenAIControllerTest {

    private static final String PROMPT = "Generate a test prompt";
    private static final String EXPECTED_RESPONSE = "Generated completion text";

    @Mock
    private OpenAIService openAIService;
    @InjectMocks
    private OpenAIController openAIController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCompletionSuccess() {
        // GIVEN
        when(openAIService.getCompletion(PROMPT)).thenReturn(Mono.just(EXPECTED_RESPONSE));

        // WHEN
        openAIController.getCompletion(PROMPT);

        // THEN
        verify(openAIService, times(1)).getCompletion(PROMPT);
    }

}

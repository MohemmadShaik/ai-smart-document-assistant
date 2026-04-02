package com.ai.documentassistant.controller;

import com.ai.documentassistant.dto.AskRequest;
import com.ai.documentassistant.dto.AskResponse;
import com.ai.documentassistant.service.AIService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/ai")
public class AIController {

    private final AIService aiService;

    public AIController(AIService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/ask")
    public AskResponse ask(@RequestBody AskRequest request) {

        String answer = aiService.askAI(request.getQuestion());

        return new AskResponse(answer);
    }
}
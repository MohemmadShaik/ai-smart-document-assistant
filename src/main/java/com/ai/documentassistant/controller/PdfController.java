package com.ai.documentassistant.controller;

import com.ai.documentassistant.service.AIService;
import com.ai.documentassistant.service.PdfService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    private final PdfService pdfService;
    private final AIService aiService;

    public PdfController(PdfService pdfService, AIService aiService) {
        this.pdfService = pdfService;
        this.aiService = aiService;
    }

    @PostMapping("/summarize")
    public String summarizePdf(@RequestParam("file") MultipartFile file) {

        // 1. Extract text
        String text = pdfService.extractText(file);

        // 2. Validate + Process
        text = pdfService.validateAndProcess(text);

        // 3. AI Summarization
        return aiService.summarize(text);
    }
}
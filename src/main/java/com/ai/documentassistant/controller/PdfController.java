package com.ai.documentassistant.controller;

import com.ai.documentassistant.service.AIService;
import com.ai.documentassistant.service.PdfService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> summarizePdf(@RequestParam("file") MultipartFile file) {
        try {
            String text = pdfService.extractText(file);
            return ResponseEntity.ok(text);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        // 2. Validate + Process
        //text = pdfService.validateAndProcess(text);

        // 3. AI Summarization
       //return aiService.summarize(text);
    }
}
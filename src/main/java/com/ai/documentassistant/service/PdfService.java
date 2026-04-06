package com.ai.documentassistant.service;

//package com.example.ai.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class PdfService {

    private static final int MAX_TEXT_LENGTH = 5000;

    public String extractText(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream();
             PDDocument document = PDDocument.load(inputStream)) {

            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);

            // STEP 3: Logging
            System.out.println("Extracted text length: " + text.length());

            return text;

        } catch (Exception e) {
            throw new RuntimeException("Error while reading PDF file", e);
        }
    }


    public String validateAndProcess(String text) {

        if (text == null || text.isBlank()) {
            throw new RuntimeException("PDF is empty or unreadable");
        }

        if (text.length() > MAX_TEXT_LENGTH) {
            System.out.println("Text too large, trimming...");
            text = text.substring(0, MAX_TEXT_LENGTH);
        }

        return text;
    }

}
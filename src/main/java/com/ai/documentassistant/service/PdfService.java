package com.ai.documentassistant.service;

//package com.example.ai.service;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class PdfService {

    private static final int MAX_TEXT_LENGTH = 5000;

    public String extractText(MultipartFile file) {

        try (InputStream inputStream = file.getInputStream();
             PDDocument document = PDDocument.load(inputStream)) {

            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);

            // ✅ Logging
            System.out.println("Extracted text length: " + (text != null ? text.length() : 0));

            // 🔍 Detect if PDF contains images (scanned PDF)
            boolean hasImages = false;

            for (PDPage page : document.getPages()) {
                if (page.getResources() != null) {
                    for (COSName name : page.getResources().getXObjectNames()) {
                        PDXObject xObject = page.getResources().getXObject(name);
                        if (xObject instanceof PDImageXObject) {
                            hasImages = true;
                            break;
                        }
                    }
                }
                if (hasImages) break;
            }

            // 🔴 EDGE CASE HANDLING
            if (text == null || text.trim().isEmpty()) {
                if (hasImages) {
                    throw new RuntimeException("Uploaded PDF file is a scanned file");
                } else {
                    throw new RuntimeException("Uploaded PDF file is an Empty file");
                }
            }

            return text;

        } catch (IOException e) {
            throw new RuntimeException("Uploaded file is a corrupted file");
        }
    }

   //-------
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
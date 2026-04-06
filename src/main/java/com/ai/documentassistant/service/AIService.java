package com.ai.documentassistant.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;

@Service
public class AIService {

    private final ChatClient chatClient;


    public AIService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String askAI(String question) {
        return chatClient.prompt(question).call().content();
    }

    public String summarize(String text) {
        return chatClient
                .prompt("Summarize this document:\n" + text)
                .call()
                .content();
    }
}

/* 👉 Why not create a new AI client every time?

You answer:

We reuse a singleton ChatClient bean for efficiency and connection reuse instead of creating new instances per request.

*/



























// PAT 3 : Converting to RestTemplate (synchronous)

/*
package com.ai.documentassistant.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class AIService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_KEY = System.getenv("GEMINI_API_KEY");

    public String askAI(String question) {

        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + API_KEY;

        Map<String, Object> requestBody = Map.of(
                "contents", new Object[]{
                        Map.of("parts", new Object[]{
                                Map.of("text", question)
                        })
                }
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                String.class
        );

        return response.getBody();
    }

}
*/




// PART 2 : Here we are using Gemini API key which is simple but not Vertex AI(GCP) which is complex and
// here we use WebClient (manual call) — very easy.
// Since spring AI currently: 👉 Doesn’t fully support simple Gemini API cleanly
// Replace AIService (Manual Gemini Call)
// Here we used Reactive (Advanced Async) nothing but a WebClient tool of types Mono and flux.
// Due to Blocking call in reactive context we switched to RestTemplate(Synchronous) which is covered in PART 3.


/*
package com.ai.documentassistant.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AIService {

    private final WebClient webClient;

    private final String API_KEY = System.getenv("GEMINI_API_KEY");

    public AIService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://generativelanguage.googleapis.com")
                .build();
    }

    public String askAI(String question) {

        String response = webClient.post()
                .uri("/v1beta/models/gemini-pro:generateContent?key=" + API_KEY)
                .bodyValue("""
                        {
                          "contents": [{
                            "parts": [{"text": "%s"}]
                          }]
                        }
                        """.formatted(question))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return response;
    }
}
*/

//-----------------------------------------------------------


// PART 1- Reactive Programming (Non-Blocking I/O)
// Here we use OPENAPI key which is a paid version. So we shifted to PART 2 were we using Gemini API key which is free
/*package com.ai.documentassistant.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AIService {


    private final ChatClient chatClient;

    // Constructor Injection (Best Practice)
    public AIService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }
    public String askAI(String question) {

        System.out.println("User Question: " + question); // debug log

        return chatClient
                .prompt()
                .user(question)
                .call()
                .content();
    }
}*/


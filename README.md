# ai-smart-document-assistant
Build a Spring Boot application where a user uploads a PDF document and the AI can:   
1. Summarize the document  
2. Answer questions from the document  
3. Extract key points  

Tech Stack:
Java 21
Spring Boot
Spring AI
OpenAI / Azure OpenAI
Apache PDFBox
IntelliJ IDEA Community
Postman

Steps                  
--------------------- 
Phase 1: Environment setup (COMPLETED and pushed to Github A/c)   
Phase 2: Project + AI Integration + First API  (COMPLETED but not pushed to Github A/c)
Phase 3: PDF + Summarizer (Partially done)
Phase 4: Testing + Final polish

Phase 1: Environment Setup (20 minutes)

✅ Environment variable configured  
✅ OpenAI key working  
✅ Application started successfully  
✅ Professional project structure aligned  

Our AI Smart Document Assistant foundation is ready.

## 🚀 Phase 2: AI Integration & First Endpoint

### 🧠 System Overview

Implemented an AI-powered processing pipeline:

`Controller → Service → Spring AI → OpenAI → Response`

This phase establishes the foundation for integrating LLM capabilities into the system with clean architecture and controlled flow.

---

### 🎯 Objective

Enable the system to process user input and generate AI responses while maintaining separation of concerns and extensibility.

---

### ⚠️ Problems Addressed

* Tight coupling between API layer and AI logic ❌
* Manual REST handling complexity ❌
* Lack of structured request-response flow ❌

---

### ✅ Key Design Decisions

* **Service Layer Abstraction**

    * AI logic moved to `AIService`
    * Improves reusability and testability

* **Spring AI (ChatClient) over manual REST**

    * Reduces boilerplate
    * Cleaner integration with OpenAI

* **Thin Controller Design**

    * Handles only HTTP responsibilities
    * Delegates business logic to service layer

---

### 🔧 Controller Responsibility

* Accept request via `AskRequest`
* Validate and forward input to service
* Return structured `AskResponse`

👉 Focus: Keep controller lightweight and maintain clear boundaries

---

### ⚙️ Service Responsibility (`AIService`)

* Interacts with OpenAI via Spring AI
* Handles prompt → response transformation
* Centralizes AI-related logic

👉 Focus: Encapsulation + scalability

---

### 🤖 AI Integration Layer

* Spring AI’s `ChatClient` used as abstraction
* Eliminates need for manual API handling
* Enables easy model switching in future

---

### 🧪 Model Selection

* Evaluated: cost vs performance
* Selected: `gpt-3.5-turbo`

👉 Reason:

* Low cost for development
* Sufficient for Q&A use cases

---

### 📤 Expected Behavior

* Accepts user question
* Returns AI-generated answer
* Structured JSON response

```json id="0x8o7r"
{
  "answer": "Generated response based on input"
}
```

---

### ⚡ System Characteristics

* Clean layered architecture
* Extensible AI integration
* Ready for multi-input processing (PDF, etc.)
* Designed for handling real-world constraints (cost, scalability)

---

### 📌 Outcome

A production-ready AI endpoint with clear separation of concerns, abstraction of AI logic, and a scalable foundation for future features like document processing.


Note : - As we are using paid OpenAI key for testing our API endpoints
  in phase 2.
-  Kindly mention the amount charged when purchasing the subscription and also make it mandatory to
  close/delete the A/c and the secret key once the project testing
  is done successfully.
-  Make sure the subscription account permanently deactivated.




Key note 1 : 
Q) Why didn’t you directly call OpenAI instead of using Spring AI?
Sol : Initially, I tried manual REST calls to OpenAI, but it added unnecessary complexity in request building and error handling.
      So I switched to Spring AI’s ChatClient, which abstracts that layer and lets me focus on business logic. 
      It also makes it easier to switch models later without changing controller or service structure.

Key note 2 : 
Q) What we did in Phase 2?
Sol : I designed a layered AI processing system with abstraction and scalability in mind.

Phase 2 summary :
----------------
Initially, I used manual REST calls with RestTemplate to integrate OpenAI. 
But I had to manually construct headers, request body, and handle response parsing, which made the service layer messy and tightly coupled to API details.
So I switched to Spring AI’s ChatClient, which abstracts those low-level details.
This allowed me to keep my service logic clean and focused only on input and output.
It also gives flexibility to switch models later without changing the controller or service structure.

Now you show:
------------
You used RestTemplate (specific)
You handled headers + body (real pain)
You improved clean architecture


Q) What if Spring AI is not available?

👉 Answer:

“I can fall back to manual REST integration since I understand the underlying API structure. Spring AI just simplifies that layer.”

Challenges faced in Phase 2 :
---------------------------
- Gemini failure experience
- RestTemplate vs WebClient confusion
- Switching to Spring AI

Phase 2 completed successfully.

--------------------**---------------------------**---------------------

Phase 3: PDF + Summarizer

## Architecture
Upload PDF
↓
Extract Text (PDFBox)
↓
Validate (Max limit + empty check)
↓
Process (trim if needed)
↓
Send to AI (Summarizer)
↓
Return Response

### Features:
- Upload PDF file
- Extract text using Apache PDFBox
- Validate content (empty + max length)
- Trim large input (5000 chars)
- Generate AI summary

### API:
POST /api/pdf/summarize

### Flow:
Upload → Extract → Validate → AI → Response

🎯 WHAT YOU JUST BUILT (BIG DEAL 🔥)

You now have:

✅ File Upload API
✅ PDF Parsing (real-world skill)
✅ AI Integration (Spring AI)
✅ Input validation (production-level)
✅ Logging
✅ Clean architecture
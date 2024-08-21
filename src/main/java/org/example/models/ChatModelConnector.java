package org.example.models;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

public class ChatModelConnector {

    private static final String LOCALHOST = "http://localhost:11434";

    public ChatLanguageModel connectModel(){
        return OllamaChatModel.builder()
                .baseUrl(LOCALHOST)
                .modelName("llama3")
                .temperature(0.4)
                .build();
    }
}

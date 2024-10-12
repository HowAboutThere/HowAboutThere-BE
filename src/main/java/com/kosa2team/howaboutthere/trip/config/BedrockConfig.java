package com.kosa2team.howaboutthere.trip.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BedrockConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder.defaultSystem("You are a friendly AI travel planner. Plesae answer in korean.")
                .build();
    }

}

package com.quest.config.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(@NonNull StompEndpointRegistry registry) {
        registry
                .addEndpoint("/ws")
                // .setAllowedOriginPatterns("*"); // USE THIS TO USE WITH POSTMAN
                .setAllowedOrigins("http://localhost:5173")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(@NonNull MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app")
                .enableSimpleBroker("/topic", "/queue");
        registry.setUserDestinationPrefix("/user");
    }
}

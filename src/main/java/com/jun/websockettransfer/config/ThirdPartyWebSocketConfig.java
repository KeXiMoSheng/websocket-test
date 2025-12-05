package com.jun.websockettransfer.config;

import com.jun.websockettransfer.handler.ThirdPartyWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class ThirdPartyWebSocketConfig implements WebSocketConfigurer {

    private final ThirdPartyWebSocketHandler handler;

    @Autowired
    public ThirdPartyWebSocketConfig(ThirdPartyWebSocketHandler handler) {
        this.handler = handler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(handler, "/ws/thirdparty")
                .setAllowedOrigins("*");
    }
}
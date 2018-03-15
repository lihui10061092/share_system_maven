package com.lihui.share.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.lihui.share.websocket.HandshakeInterceptor;
import com.lihui.share.websocket.MyWebSocketHandler;


@Configuration
@EnableWebSocket//开启websocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new MyWebSocketHandler(), "/echo.do").addInterceptors(new HandshakeInterceptor()).setAllowedOrigins("*");
        registry.addHandler(new MyWebSocketHandler(), "/sockjs/echo.do").addInterceptors(new HandshakeInterceptor()).setAllowedOrigins("*").withSockJS();
    }

}

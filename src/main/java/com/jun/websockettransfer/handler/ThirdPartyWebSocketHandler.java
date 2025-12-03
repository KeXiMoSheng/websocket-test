package com.jun.websockettransfer.handler;

import com.jun.websockettransfer.utils.WebsocketSessionUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;


@Component
public class ThirdPartyWebSocketHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.printf("中转客户端[%s]连接第三方服务器%n", session.getId());
        Map<String, String> params = WebsocketSessionUtil.getParamsFromSession(session);
        System.out.println("连接参数：" + params);
        // 发送欢迎消息
        WebsocketSessionUtil.sendMessage(session, "欢迎连接第三方服务器！");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String payload = message.getPayload();
        System.out.printf("收到中转服务消息：%s%n", payload);
        // 回复消息（模拟业务处理）
        WebsocketSessionUtil.sendMessage(session, "第三方服务器已接收：" + payload);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {
        System.out.printf("中转客户端[%s]断开连接%n", session.getId());
    }
}
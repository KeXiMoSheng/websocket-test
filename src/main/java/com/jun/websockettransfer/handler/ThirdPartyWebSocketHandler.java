package com.jun.websockettransfer.handler;

import com.jun.websockettransfer.utils.WebsocketSessionUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.nio.ByteBuffer;
import java.util.Map;


@Component
public class ThirdPartyWebSocketHandler extends AbstractWebSocketHandler {

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
        System.out.println("收到中转服务文本消息：" + payload);
        // 回复消息（模拟业务处理）
        WebsocketSessionUtil.sendMessage(session, "第三方服务器已接收文本消息：" + payload);
    }
    /**
     * 处理前端发送的 PCM 音频二进制流
     */
    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        //获取前端发送的原始二进制流
        ByteBuffer byteBuffer = message.getPayload();
        //将数据存入byte数组
        byte[] audioBytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(audioBytes);
        //模拟处理数据
        WebsocketSessionUtil.sendMessage(session, "PCM音频数据已接收");
    }

    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        super.handlePongMessage(session, message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println("中转客户端[" + session.getId() + "]断开连接");
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("中转客户端[" + session.getId() + "]传输错误：" + exception.getMessage());
    }
}
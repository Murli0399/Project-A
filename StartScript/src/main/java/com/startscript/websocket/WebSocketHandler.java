package com.startscript.websocket;


import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class WebSocketHandler extends TextWebSocketHandler {

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Logic to handle incoming messages and send notifications
        session.sendMessage(new TextMessage("Message received: " + message.getPayload()));
    }
}

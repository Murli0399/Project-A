package com.startscript.service;


import com.startscript.model.Notification;
import com.startscript.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class NotificationService {

    private final Set<WebSocketSession> sessions = new HashSet<>();

    @Autowired
    private NotificationRepository notificationRepository;

    public void addSession(WebSocketSession session) {
        sessions.add(session);
    }

    public void removeSession(WebSocketSession session) {
        sessions.remove(session);
    }

    public void sendNotification(String message) throws IOException {
        // Save notification to MongoDB
        Notification notification = new Notification(message);
        notificationRepository.save(notification);

        // Send notification to WebSocket clients
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(message));
            }
        }
    }
}

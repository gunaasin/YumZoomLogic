package com.guna.yumzoom.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderWebSocketController {
    private final SimpMessagingTemplate messagingTemplate;

    public void sendOrderStatus(String orderId, String status) {
        log.info("🚀 Sending WebSocket message to: /topic/order/{}", orderId);
        log.info("📩 Order Status: {}", status);
        messagingTemplate.convertAndSend("/topic/order/" + orderId, status);
    }
}
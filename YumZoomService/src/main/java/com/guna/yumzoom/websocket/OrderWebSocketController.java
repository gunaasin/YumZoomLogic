package com.guna.yumzoom.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class OrderWebSocketController {
    private final SimpMessagingTemplate messagingTemplate;

    public void sendOrderStatus(String orderId, String status) {
        messagingTemplate.convertAndSend("/topic/order/" + orderId, "Order Update: " + status);
    }
}
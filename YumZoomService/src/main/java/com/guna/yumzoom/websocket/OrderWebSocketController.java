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
        messagingTemplate.convertAndSend("/topic/order/" + orderId, status);
    }

    public void sendOrderStatusForRestaurant(String restaurantId , String status){
//        log.info("🚀 Sending WebSocket message to: /topic/rest_orders/{}", restaurantId);
//        log.info("📩 Order Status: {}", status);
        messagingTemplate.convertAndSend("/topic/rest_orders/"+ restaurantId , status);
    }

    public void sendOrdersForDeliveryAgents(String orderId){
        messagingTemplate.convertAndSend("/topic/new_order",orderId);
    }

}
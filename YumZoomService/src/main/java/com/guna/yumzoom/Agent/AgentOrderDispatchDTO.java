package com.guna.yumzoom.Agent;

import lombok.Builder;

@Builder
public record AgentOrderDispatchDTO(
        String orderId,
        String deliveryLocation,
        String customerNumber,
        String pickupLocation,
        String status
) {
}

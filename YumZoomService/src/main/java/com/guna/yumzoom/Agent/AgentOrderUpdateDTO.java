package com.guna.yumzoom.Agent;

import lombok.Builder;

@Builder
public record AgentOrderUpdateDTO(
        String orderId,
        String status,
        String token
) {
}

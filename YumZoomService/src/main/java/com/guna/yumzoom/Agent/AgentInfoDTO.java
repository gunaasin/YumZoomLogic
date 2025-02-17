package com.guna.yumzoom.Agent;

import lombok.Builder;

@Builder
public record AgentInfoDTO(
        String name,
//        String address,
        String email,
        String status
) {
}

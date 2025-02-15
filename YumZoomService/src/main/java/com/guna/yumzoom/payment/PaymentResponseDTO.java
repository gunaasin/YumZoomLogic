package com.guna.yumzoom.payment;

import lombok.Builder;

@Builder
public record PaymentResponseDTO(
        String status,
        String message,
        String sessionId,
        String sessionUrl
) {
}

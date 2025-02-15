package com.guna.yumzoom.payment;

import lombok.Builder;

@Builder
public record PaymentInfoDTO(
        String URL,
        String message
) {
}

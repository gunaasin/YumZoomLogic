package com.guna.yumzoom.payment;

import lombok.Builder;

@Builder
public record PaymentRequestDTO(
        String token,
        String paymentMode
) {
}
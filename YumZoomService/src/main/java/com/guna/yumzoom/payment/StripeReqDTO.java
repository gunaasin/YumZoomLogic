package com.guna.yumzoom.payment;

import lombok.Builder;

@Builder
public record StripeReqDTO(
        String name,
        int quantity,
        Long totalAmount,
        String email
) {
}

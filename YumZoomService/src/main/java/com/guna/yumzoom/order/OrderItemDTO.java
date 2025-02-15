package com.guna.yumzoom.order;

import lombok.Builder;

@Builder
public record OrderItemDTO(
        String foodImg,
        String name,
        int originalAmount,
        int totalAmount,
        int quantity
) {
}

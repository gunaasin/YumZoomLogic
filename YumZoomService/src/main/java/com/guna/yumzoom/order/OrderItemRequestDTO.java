package com.guna.yumzoom.order;

import lombok.Builder;

@Builder
public record OrderItemRequestDTO(
        int productId,
        int quantity
) {
}

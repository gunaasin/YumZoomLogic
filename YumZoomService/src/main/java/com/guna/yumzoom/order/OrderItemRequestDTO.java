package com.guna.yumzoom.order;

public record OrderItemRequestDTO(
        int productId,
        int quantity
) {
}

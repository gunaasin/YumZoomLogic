package com.guna.yumzoom.cart;

public record CartRequestDTO(
    String customerEmail,
    int productId,
    int quantity
) {
}

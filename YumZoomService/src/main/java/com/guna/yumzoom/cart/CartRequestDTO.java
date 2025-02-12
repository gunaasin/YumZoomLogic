package com.guna.yumzoom.cart;

public record CartRequestDTO(
    String token,
    int productId
) {
}

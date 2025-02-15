package com.guna.yumzoom.cartitem;

import lombok.Builder;

@Builder
public record CartItemResponseDTO(
        String foodName,
        String restaurantName,
        int quantity,
        int price,
        int id,
        int foodId,
        String category
) {
}

package com.guna.yumzoom.menu;

import lombok.Builder;

@Builder
public record FoodRequestDTO(
    String itemName,
    String description,
    int price,
    String category,
    String imagePath,
    String restaurantId
) {
}

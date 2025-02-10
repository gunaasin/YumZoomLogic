package com.guna.yumzoom.menu;

import lombok.Builder;

@Builder
public record FoodResponseDTO(
        Integer id,
        String itemName,
        String description,
        int price,
        float rating,
        String category,
        String imagePath
) {
}

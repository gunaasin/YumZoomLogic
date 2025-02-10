package com.guna.yumzoom.menu;

public record UpdateFoodDTO(
        int foodId,
        String itemName,
        String description,
        int price,
        String category,
        String imagePath,
        String restaurantId
) {
}

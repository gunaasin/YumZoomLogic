package com.guna.yumzoom.restaurant;

import lombok.Builder;

@Builder
public record RestaurantInformationDTO(
        String name,
        String restaurantId,
        float rating,
        String cusineType,
        boolean isActive,
        String image
) {
}

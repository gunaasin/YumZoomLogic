package com.guna.yumzoom.restaurant;

import com.guna.yumzoom.menu.FoodResponseDTO;
import lombok.Builder;
import java.util.List;

@Builder
public record RestaurantOrderDTO(
        String orderId,
        String status,
        List<FoodResponseDTO> foodList
) {
}

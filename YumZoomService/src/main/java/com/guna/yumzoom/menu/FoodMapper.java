package com.guna.yumzoom.menu;

import org.springframework.stereotype.Service;

@Service
public class FoodMapper {

    public Food convertRequestToFood(FoodRequestDTO foodRequestDTO){
        return Food.builder()
                .category(foodRequestDTO.category())
                .description(foodRequestDTO.description())
                .itemName(foodRequestDTO.itemName())
                .imagePath(foodRequestDTO.imagePath())
                .price(foodRequestDTO.price())
                .build();
    }


    public FoodResponseDTO convertFoodToResponse(Food food){
        return FoodResponseDTO.builder()
                .category(food.getCategory())
                .id(food.getId())
                .imagePath(food.getImagePath())
                .description(food.getDescription())
                .price(food.getPrice())
                .itemName(food.getItemName())
                .rating(food.getRating())
                .build();
    }

}

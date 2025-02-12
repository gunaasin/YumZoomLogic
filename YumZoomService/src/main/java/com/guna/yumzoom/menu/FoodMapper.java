package com.guna.yumzoom.menu;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodMapper {

    private final FoodRepo foodRepo;

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

    public FoodSuggestionResponseDTO convertFoodSuggestion(Food food){
        return FoodSuggestionResponseDTO.builder()
                .category(food.getCategory())
                .id(food.getId())
                .imagePath(food.getImagePath())
                .description(food.getDescription())
                .price(food.getPrice())
                .itemName(food.getItemName())
                .rating(food.getRating())
                .restaurantName(food.getRestaurant().getName())
                .build();
    }

}

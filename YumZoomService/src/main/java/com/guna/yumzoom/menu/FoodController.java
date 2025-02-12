package com.guna.yumzoom.menu;

import com.guna.yumzoom.restaurant.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class FoodController {
    private final RestaurantService restaurantService;

    @GetMapping("/get/menu/suggestions")
    public ResponseEntity<?> getFoodsBasedOnSuggestions(@RequestParam String keyword){
        return ResponseEntity.ok().body(restaurantService.getFoodListSuggestions(keyword));
    }

    @GetMapping("/get/menuBySuggestion")
    public ResponseEntity<?> getFoodsBased(@RequestParam String keyword){
        return ResponseEntity.ok().body(restaurantService.getRelatedFoodBasedOnSuggestion(keyword));
    }

}

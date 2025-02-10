package com.guna.yumzoom.restaurant;

import com.guna.yumzoom.menu.FoodRequestDTO;
import com.guna.yumzoom.menu.RemoveFoodItemRequest;
import com.guna.yumzoom.menu.UpdateFoodDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping("/restaurant/addFoodItem")
    public ResponseEntity<?> addFoodsInRestaurant(@RequestBody FoodRequestDTO foodRequestDTO){
        try{
            return ResponseEntity.ok().body(restaurantService.addFood(foodRequestDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message","BadRequest"));
        }
    }

    @PutMapping("/restaurant/updateFoodItem")
    public ResponseEntity<?> updateFoodItem(@RequestBody UpdateFoodDTO updateFoodDTO){
        try{
            System.out.println(updateFoodDTO);
            var res = restaurantService.updateFood(updateFoodDTO);
            return ResponseEntity.ok().body(Map.of("message",res));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message","BadRequest"));
        }
    }

    @DeleteMapping("/restaurant/removeFoodItem")
    public ResponseEntity<?> removeFoodItem(@RequestBody RemoveFoodItemRequest request){
        try{
            restaurantService.removeFoodItem(request);
            return ResponseEntity.ok().body(Map.of("message","Food item removed"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message","BadRequest"));
        }
    }

    @GetMapping("/restaurant/getAllFood")
    public ResponseEntity<?> getAllFoodsByRestaurant(@RequestParam String id){
        return ResponseEntity.ok().body(restaurantService.getAllFoods(id));
    }

    @PutMapping("/restaurant/updateRestaurantInfo")
    public ResponseEntity<?> restaurantInfoUpdate(
            @RequestBody RestaurantInfoUpdateRequestDTO dto
    ){
        try{
            System.out.println(dto +" this one is dto");
            restaurantService.updateRestaurantInfo(dto);
            return ResponseEntity.ok().body(Map.of("message","update succeed"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message","BadRequest"));
        }
    }

    @GetMapping("/restaurant/information")
    public ResponseEntity<?> getInformation(@RequestParam String token){
        return ResponseEntity.ok().body(restaurantService.getInformationAboutRestaurant(token));
    }



}

package com.guna.yumzoom.restaurant;

import com.guna.yumzoom.menu.Food;
import com.guna.yumzoom.menu.FoodMapper;
import com.guna.yumzoom.menu.FoodRepo;
import com.guna.yumzoom.menu.FoodResponseDTO;
import com.guna.yumzoom.restaurantorder.RestaurantOrder;
import com.guna.yumzoom.security.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantMapper {

    private final FoodRepo foodRepo;
    private final FoodMapper foodMapper;

     static RestaurantInformationDTO restaurantToDTO(Restaurant restaurant)  {
         String encryptedId;
         try {
            encryptedId = EncryptionUtil.encrypt(restaurant.getRestaurantId());
         } catch (Exception e) {
             throw new RuntimeException(e);
         }
         return RestaurantInformationDTO.builder()
                .restaurantId(encryptedId)
                .name(restaurant.getName())
                .image(restaurant.getImageData())
                .isActive(restaurant.isActive())
                .cusineType(restaurant.getCusineType())
                .rating(restaurant.getRating())
                .build();
    }

   RestaurantOrderDTO convertRestaurantOrder(RestaurantOrder restaurantOrder){
         List<Integer> foodIds = Arrays.stream(restaurantOrder.getFoodList().split(","))
                 .map(String::trim)
                 .map(Integer::parseInt)
                 .toList();

         List<Food> foodList = new ArrayList<>();

         for(int id : foodIds){
             foodList.add(foodRepo.findById(id).orElseThrow());
         }

         List<FoodResponseDTO> foodResponseDTO = foodList.stream()
                     .map(foodMapper::convertFoodToResponse)
                     .toList();


         return RestaurantOrderDTO.builder()
                 .orderId(restaurantOrder.getOrderId())
                 .status(restaurantOrder.getStatus())
                 .foodList(foodResponseDTO)
                 .build();
    }


}

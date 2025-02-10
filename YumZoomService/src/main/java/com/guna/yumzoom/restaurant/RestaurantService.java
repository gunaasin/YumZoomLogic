package com.guna.yumzoom.restaurant;

import com.guna.yumzoom.address.Address;
import com.guna.yumzoom.address.AddressRepo;
import com.guna.yumzoom.menu.*;
import com.guna.yumzoom.security.JwtService;
import com.guna.yumzoom.user.Role;
import com.guna.yumzoom.user.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepo restaurantRepo;
    private final FoodRepo foodRepo;
    private final FoodMapper foodMapper;
    private final AddressRepo addressRepo;
    private final JwtService jwtService;
    private final UserRepo userRepo;

    public FoodResponseDTO addFood(FoodRequestDTO foodRequestDTO){
        Restaurant restaurant = restaurantRepo.findByRestaurantId(foodRequestDTO.restaurantId());
        var food = Food.builder()
                .price(foodRequestDTO.price())
                .description(foodRequestDTO.description())
                .category(foodRequestDTO.category())
                .itemName(foodRequestDTO.itemName())
                .imagePath(foodRequestDTO.imagePath())
                .restaurant(restaurant)
                .build();
        return foodMapper.convertFoodToResponse(foodRepo.save(food));
    }

    public List<FoodResponseDTO> getAllFoods(String restaurantId) {
        var resId = restaurantRepo.findByRestaurantId(restaurantId).getId();
        List<Food> foodList =  foodRepo.findAllFoodsByRestaurantId(resId);
        Collections.shuffle(foodList);
        return foodList.stream()
                .map(foodMapper::convertFoodToResponse)
                .collect(Collectors.toList());
    }

    public void removeFoodItem(RemoveFoodItemRequest request) {
         foodRepo.deleteById(request.foodId());
    }

    @Transactional
    public Object updateFood(UpdateFoodDTO updateFoodDTO) {
//        var resId = restaurantRepo.findByRestaurantId(updateFoodDTO.restaurantId()).getId();
        var food = foodRepo.findByIdAndRestaurantId(updateFoodDTO.foodId());
        if(food.isPresent()){
            var updFood = food.get();
            updFood.setPrice(updateFoodDTO.price());
            updFood.setCategory(updateFoodDTO.category());
            updFood.setDescription(updateFoodDTO.description());
            updFood.setImagePath(updateFoodDTO.imagePath());
            updFood.setItemName(updateFoodDTO.itemName());
            foodRepo.save(updFood);
            return "product updated";
        }else{
            return new Exception("Food item not found");
        }
    }

    @Transactional
    public void updateRestaurantInfo(RestaurantInfoUpdateRequestDTO dto)  {
            var restaurant = restaurantRepo.findByRestaurantId(dto.restaurantId());
            var address = dto.addressRequestDTO();
            restaurant.setName(dto.name());
            restaurant.setActive(dto.isActive());
            restaurant.setCusineType(dto.cusineType());
            restaurant.setPhone(dto.phone());
            restaurant.setImageData(dto.image());

            restaurantRepo.save(restaurant);

            var add = restaurant.getUser().getAddress();
            add.setStreet(address.street());
            add.setState(address.state());
            add.setCity(address.city());
            add.setPinCode(address.pinCode());
            addressRepo.save(add);

    }

    public RestaurantInformationDTO getInformationAboutRestaurant(String token) {
        String email = jwtService.extractMailId(token);
        String role = jwtService.extractRoles(token).getFirst();
        if(jwtService.validateToken(token,email) && role.equals(Role.RESTAURANT.name())){
            var adminId = userRepo.findByEmail(email).getId();
            var restaurant = restaurantRepo.findByRestaurantAdminId(adminId);

            return RestaurantInformationDTO.builder()
                    .name(restaurant.getName())
                    .restaurantId(restaurant.getRestaurantId())
                    .rating(restaurant.getRating())
                    .cusineType(restaurant.getCusineType())
                    .isActive(restaurant.isActive())
                    .image(restaurant.getImageData())
                    .build();
        }else{
            return null;
        }
    }


}

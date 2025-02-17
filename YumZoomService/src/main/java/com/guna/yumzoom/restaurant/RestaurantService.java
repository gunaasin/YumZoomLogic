package com.guna.yumzoom.restaurant;

import com.guna.yumzoom.address.AddressRepo;
import com.guna.yumzoom.menu.*;
import com.guna.yumzoom.order.Order;
import com.guna.yumzoom.order.OrderRepo;
import com.guna.yumzoom.order.OrderStatus;
import com.guna.yumzoom.restaurantorder.RestaurantOrder;
import com.guna.yumzoom.restaurantorder.RestaurantOrderRepo;
import com.guna.yumzoom.security.EncryptionUtil;
import com.guna.yumzoom.security.JwtService;
import com.guna.yumzoom.user.Role;
import com.guna.yumzoom.user.UserRepo;
import com.guna.yumzoom.util.Util;
import com.guna.yumzoom.websocket.OrderWebSocketController;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepo restaurantRepo;
    private final RestaurantMapper restaurantMapper;
    private final FoodRepo foodRepo;
    private final FoodMapper foodMapper;
    private final AddressRepo addressRepo;
    private final JwtService jwtService;
    private final UserRepo userRepo;
    private final RestaurantOrderRepo restaurantOrderRepo;
    private final OrderRepo orderRepo;
    private final OrderWebSocketController orderWebSocketController;

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


    // this one for users
    public List<FoodResponseDTO> getAllFoods(String restaurantId) {
        String decryptedId = null;
        try{
             decryptedId = EncryptionUtil.decrypt(restaurantId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        var resId = restaurantRepo.findByRestaurantId(decryptedId).getId();
        List<Food> foodList =  foodRepo.findAllFoodsByRestaurantId(resId);
        Collections.shuffle(foodList);
        return foodList.stream()
                .map(foodMapper::convertFoodToResponse)
                .collect(Collectors.toList());
    }
    public List<FoodResponseDTO> getAllFoodsForRest(String restaurantId) {
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


    public List<RestaurantInformationDTO> getAllRestaurant() {
        List<Restaurant> restaurantList =restaurantRepo.findAll();
        Collections.shuffle(restaurantList);
        return restaurantList.stream()
                .map(RestaurantMapper::restaurantToDTO)
                .collect(Collectors.toList());
    }

    public List<String> getListOfSuggestions(String keyword) {
        List<String> results;
        try{
            results = foodRepo.findFoodSuggestions(keyword);
            if (results.isEmpty()) {
                results = restaurantRepo.findRestaurantSuggestions(keyword);
                if(results.isEmpty()){
                    results = restaurantRepo.findCusBasedRestaurant(keyword);
                }
            }
        } catch (RuntimeException e) {
            results = restaurantRepo.findRestaurantSuggestions(keyword);
        }
        return results.isEmpty() ? List.of("no suggestions") : results;
    }

    public List<?> getRelatedResponse(String keyword) {
        List<?> result;
        try {
            result = foodRepo.findAllFoodsByKeyWord(keyword)
                    .stream()
                    .map(foodMapper::convertFoodSuggestion)
                    .toList();
            if(result.isEmpty()){
                result = restaurantRepo.findRestaurantsByName(keyword)
                        .stream()
                        .map(RestaurantMapper::restaurantToDTO)
                        .toList();
                if(result.isEmpty()){
                    result = restaurantRepo.findRestaurantByCusType(keyword)
                            .stream()
                            .map(RestaurantMapper::restaurantToDTO)
                            .toList();
                }
            }
        } catch (RuntimeException e) {
            result = restaurantRepo.findRestaurantsByName(keyword);
        }
        return result.isEmpty() ? List.of("no suggestions") : result;
    }

    public List<String> getFoodListSuggestions(String keyword) {
        List<String> results = null;
        try{
            results = foodRepo.findFoodSuggestions(keyword);
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
        return results == null ? List.of("no suggestions") : results;
    }

    public List<?> getRelatedFoodBasedOnSuggestion(String keyword){

        List<FoodResponseDTO> result = null;
        try{
            result = foodRepo.findAllFoodsByKeyWord(keyword)
                    .stream()
                    .map(foodMapper::convertFoodToResponse)
                    .toList();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return result == null ? List.of("no food item is there") : result;
    }

    public List<RestaurantOrderDTO> getOrders(String id) {
        return   restaurantRepo.findByRestaurantId(id).getRestaurantOrders()
                .stream()
                .map(restaurantMapper::convertRestaurantOrder)
                .toList();


    }

    public String updateOrder(String orderId, String status) {
        RestaurantOrder restaurantOrder = restaurantOrderRepo.findByOrderId(orderId);
        Order order = orderRepo.findByOrderId(orderId);
        if(Util.isValidOrderStatus(status)){
            restaurantOrder.setStatus(status);
            restaurantOrderRepo.save(restaurantOrder);
            orderWebSocketController.sendOrderStatus(orderId,status);
            if(status.equals(OrderStatus.PREPARING.name())) order.setStatus(OrderStatus.PREPARING.name());
            else if(status.equals(OrderStatus.READY.name())) {
                order.setStatus(OrderStatus.READY_TO_SHIFT.name());
                orderWebSocketController.sendOrdersForDeliveryAgents(orderId);
            }
            orderRepo.save(order);
            return "Order Updated";
        }else{
            return "Invalid order status: " + status;
        }
    }


}

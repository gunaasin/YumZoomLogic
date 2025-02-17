package com.guna.yumzoom.restaurantorder;
import com.guna.yumzoom.menu.Food;
import com.guna.yumzoom.order.Order;
import com.guna.yumzoom.order.OrderStatus;
import com.guna.yumzoom.orderitem.OrderItem;
import com.guna.yumzoom.restaurant.Restaurant;
import com.guna.yumzoom.restaurant.RestaurantRepo;
import com.guna.yumzoom.security.EncryptionUtil;
import com.guna.yumzoom.websocket.OrderWebSocketController;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class RestaurantOrderService {

    private final RestaurantRepo restaurantRepo;
    private final OrderWebSocketController webSocketController;

    @Transactional
    public void saveOrders(Order order) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        // Step 1: Group order items by restaurant
        Map<Integer, List<Food>> restaurantOrdersMap = new HashMap<>();

        for (OrderItem item : order.getOrderItems()) {
            Integer restaurantId = item.getFood().getRestaurant().getId();
            // Add item to the respective restaurant's list
            restaurantOrdersMap.computeIfAbsent(restaurantId, k -> new ArrayList<>()).add(item.getFood());
        }


        for (Map.Entry<Integer, List<Food>> entry : restaurantOrdersMap.entrySet()) {
            Integer restaurantId = entry.getKey();
            String foodIds = entry.getValue().stream()
                    .map(food -> String.valueOf(food.getId()))
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("");

            Restaurant restaurant = restaurantRepo.findById(restaurantId).orElseThrow();

            RestaurantOrder restaurantOrder = RestaurantOrder.builder()
                    .orderId(order.getOrderId())
                    .restaurant(restaurant)
                    .foodList(foodIds)
                    .time(formattedDateTime)
                    .status(OrderStatus.NEW.name())
                    .build();


            List<RestaurantOrder> currentOrders = restaurant.getRestaurantOrders();
            if (currentOrders == null) {
                currentOrders = new ArrayList<>();
            }
            currentOrders.add(restaurantOrder);
            restaurant.setRestaurantOrders(currentOrders);
            Restaurant restaurant1 = restaurantRepo.save(restaurant);
            System.out.println(restaurant1.getRestaurantId() +" this one for enc id") ;
            webSocketController.sendOrderStatusForRestaurant(restaurant1.getRestaurantId(), OrderStatus.NEW.name());
        }
    }

}

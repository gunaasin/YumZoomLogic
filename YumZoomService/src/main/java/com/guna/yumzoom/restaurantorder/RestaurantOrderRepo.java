package com.guna.yumzoom.restaurantorder;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantOrderRepo extends JpaRepository<RestaurantOrder , Integer> {
    RestaurantOrder findByOrderId(String orderId);
}

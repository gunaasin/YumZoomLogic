package com.guna.yumzoom.restaurantorder;

import com.guna.yumzoom.menu.Food;
import com.guna.yumzoom.restaurant.Restaurant;
import jakarta.persistence.*;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class RestaurantOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    String orderId;

//    Long totalAmount;

    String time;

    String status;

    private String foodList;

    @ManyToOne
    @JoinColumn(name = "restaurant_id" , nullable = false)
    @ToString.Exclude
    private Restaurant restaurant;
}

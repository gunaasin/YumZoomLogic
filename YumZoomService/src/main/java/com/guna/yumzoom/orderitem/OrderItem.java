package com.guna.yumzoom.orderitem;

import com.guna.yumzoom.menu.Food;
import com.guna.yumzoom.order.Order;
import com.guna.yumzoom.restaurantorder.RestaurantOrder;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int quantity;
    private int totalPrice;

    @ManyToOne
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    @ManyToOne
    @JoinColumn(name = "order_id",nullable = false)
    private Order order;

}

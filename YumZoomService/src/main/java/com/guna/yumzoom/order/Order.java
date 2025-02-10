package com.guna.yumzoom.order;

import com.guna.yumzoom.orderitem.OrderItem;
import com.guna.yumzoom.restaurant.Restaurant;
import com.guna.yumzoom.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String orderId;

    @ManyToOne
    @JoinColumn(name = "customer_id" , nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id" , nullable = false)
    private Restaurant restaurant;
    private String orderDate;
    private int totalAmount;
    private OrderStatus status;
    private String paymentMode;
    private String imagePath;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL , orphanRemoval = true)
    private List<OrderItem> orderItems;
}

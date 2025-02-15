package com.guna.yumzoom.order;

import com.guna.yumzoom.orderitem.OrderItem;
import com.guna.yumzoom.restaurant.Restaurant;
import com.guna.yumzoom.user.User;
import jakarta.persistence.*;
import lombok.*;

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
    @ToString.Exclude
    private User user;
    private String orderDate;
    private int totalAmount;
    private String status;
    private String paymentMode;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL , orphanRemoval = true)
    @ToString.Exclude
    private List<OrderItem> orderItems;
}

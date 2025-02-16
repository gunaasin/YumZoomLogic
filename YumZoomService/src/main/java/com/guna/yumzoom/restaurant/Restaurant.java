package com.guna.yumzoom.restaurant;

import com.guna.yumzoom.menu.Food;
import com.guna.yumzoom.restaurantorder.RestaurantOrder;
import com.guna.yumzoom.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_seq")
    @SequenceGenerator(name = "restaurant_seq", sequenceName = "restaurant_id_seq", initialValue = 1000, allocationSize = 1)
    private Integer id;

    @Column(nullable = false,
            unique = true,
            updatable = false)
    private String restaurantId;

    private String name;

    @Column(unique = true)
    private String phone;

    private float rating;
    private String cusineType;
    private boolean isActive;

    private String imageData;

    @OneToOne
    @JoinColumn(name = "restaurant_admin_id" , unique = true , nullable = false)
    private User user;

    @OneToMany(
            mappedBy = "restaurant",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @ToString.Exclude
    private List<Food> foodList;

    @OneToMany(
            mappedBy = "restaurant",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @ToString.Exclude
    private List<RestaurantOrder> restaurantOrders;


}

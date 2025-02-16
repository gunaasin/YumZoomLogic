package com.guna.yumzoom.menu;

import com.guna.yumzoom.restaurant.Restaurant;
import com.guna.yumzoom.restaurantorder.RestaurantOrder;
import com.guna.yumzoom.restaurantorder.RestaurantOrderRepo;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "food_seq")
    @SequenceGenerator(name = "food_seq", sequenceName = "food_id_seq", initialValue = 100, allocationSize = 1)
    private Integer id;
    @Column(
            nullable = false
    )
    private String  itemName;
    @Column(
            nullable = false
    )
    private String description;
    @Column(
            nullable = false
    )
    private int price;
    private float rating;
    @Column(
            nullable = false
    )
    private String category ;
    @Column(
            nullable = false
    )
    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "restaurant_id" , nullable = false)
    @ToString.Exclude
    private Restaurant restaurant;


}

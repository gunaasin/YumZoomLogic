package com.guna.yumzoom.cartitem;

import com.guna.yumzoom.cart.Cart;
import com.guna.yumzoom.menu.Food;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    @ToString.Exclude
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "food_id", nullable = false, unique = true)
    private Food food;

    private Integer quantity;
    private Integer totalPrice;
}

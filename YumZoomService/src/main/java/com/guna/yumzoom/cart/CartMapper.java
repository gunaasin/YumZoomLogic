package com.guna.yumzoom.cart;

import com.guna.yumzoom.cartitem.CartItem;
import com.guna.yumzoom.menu.Food;
import com.guna.yumzoom.menu.FoodRepo;
import com.guna.yumzoom.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartMapper {
    private final UserRepo userRepo;
    private final CartRepo cartRepo;
    private final FoodRepo foodRepo;

    public CartItem convertRequestToCart(CartRequestDTO cartRequestDTO){
        var user = userRepo.findByEmail(cartRequestDTO.customerEmail());
        var cart = cartRepo.findByUserId(user.getId());
        var food = foodRepo.findById(cartRequestDTO.productId()).orElseThrow();
        return CartItem.builder()
                .cart(cart)
                .food(food)
                .quantity(cartRequestDTO.quantity())
                .totalPrice(food.getPrice() * cartRequestDTO.quantity())
                .build();
    }
}

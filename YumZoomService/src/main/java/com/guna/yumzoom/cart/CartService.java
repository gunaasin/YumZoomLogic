package com.guna.yumzoom.cart;

import com.guna.yumzoom.cartitem.CartItem;
import com.guna.yumzoom.cartitem.CartItemRepo;
import com.guna.yumzoom.menu.FoodRepo;
import com.guna.yumzoom.security.JwtService;
import com.guna.yumzoom.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartMapper cartMapper;
    private final UserRepo userRepo;
    private final CartRepo cartRepo;
    private final FoodRepo foodRepo;
    private final JwtService jwtService;
    private final CartItemRepo cartItemRepo;

    public void addToCart(CartRequestDTO cartRequestDTO) {
        var user = userRepo.findByEmail(jwtService.extractMailId(cartRequestDTO.token()));
        var cart = cartRepo.findByUserId(user.getId());
        var food = foodRepo.findById(cartRequestDTO.productId())
                .orElseThrow(() -> new RuntimeException("Food item not found"));
        CartItem existingCartItem = cartItemRepo.findByCartAndFood(cart, food);
        if (existingCartItem!=null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
            existingCartItem.setTotalPrice(existingCartItem.getQuantity() * food.getPrice());
            cartItemRepo.save(existingCartItem);
        } else {
            var cartItem = CartItem.builder()
                    .cart(cart)
                    .food(food)
                    .quantity(1)
                    .totalPrice(food.getPrice())
                    .build();
            cartItemRepo.save(cartItem);
        }
    }


}

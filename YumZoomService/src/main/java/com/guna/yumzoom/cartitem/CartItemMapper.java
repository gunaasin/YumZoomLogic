package com.guna.yumzoom.cartitem;

import org.springframework.stereotype.Service;


@Service
public class CartItemMapper{

    public static CartItemResponseDTO convertCartItemToResponse(CartItem cartItem){
        return CartItemResponseDTO.builder()
                .foodName(cartItem.getFood().getItemName())
                .category(cartItem.getFood().getCategory())
                .restaurantName(cartItem.getFood().getRestaurant().getName())
                .quantity(cartItem.getQuantity())
                .price(cartItem.getTotalPrice())
                .foodId(cartItem.getFood().getId())
                .id(cartItem.getId())
                .build();
    }
}

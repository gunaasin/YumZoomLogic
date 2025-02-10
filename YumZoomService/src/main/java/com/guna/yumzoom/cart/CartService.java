package com.guna.yumzoom.cart;

import com.guna.yumzoom.cartitem.CartItemRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartMapper cartMapper;
    private final CartItemRepo cartItemRepo;

    public void addToCart(CartRequestDTO cartRequestDTO) {
        cartItemRepo.save(cartMapper.convertRequestToCart(cartRequestDTO));
    }
}

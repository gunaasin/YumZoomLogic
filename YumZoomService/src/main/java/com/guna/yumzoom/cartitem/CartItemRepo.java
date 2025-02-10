package com.guna.yumzoom.cartitem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepo extends JpaRepository<CartItem,Integer> {
}

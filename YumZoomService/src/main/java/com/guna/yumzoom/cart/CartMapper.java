package com.guna.yumzoom.cart;

import com.guna.yumzoom.menu.FoodRepo;
import com.guna.yumzoom.security.JwtService;
import com.guna.yumzoom.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartMapper {
    private final UserRepo userRepo;
    private final CartRepo cartRepo;
    private final FoodRepo foodRepo;
    private final JwtService jwtService;


}

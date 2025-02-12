package com.guna.yumzoom.customer;

import com.guna.yumzoom.cart.Cart;
import com.guna.yumzoom.cart.CartRepo;
import com.guna.yumzoom.cartitem.CartItem;
import com.guna.yumzoom.security.JwtService;
import com.guna.yumzoom.user.User;
import com.guna.yumzoom.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final JwtService jwtService;
    private final UserRepo userRepo;
    private final CartRepo cartRepo;

    CustomerInfoDTO getCustomerInformation(String token){
        String email = jwtService.extractMailId(token);
        User user = userRepo.findByEmail(email);
        return CustomerInfoDTO.builder()
                .name(user.getUsername())
                .email(user.getEmail())
                .build();


    }

    public List<?> getCartList(String token) {
        Cart cart = cartRepo.findByUserId(
                userRepo.findByEmail(jwtService.extractMailId(token)).getId());
        List<CartItem> cartItemList = cart.getCartItems();

        if(cartItemList.isEmpty()){
            return List.of("Cart is Empty");
        }
        return cartItemList;
    }
}

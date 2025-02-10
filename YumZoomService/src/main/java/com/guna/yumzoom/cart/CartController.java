package com.guna.yumzoom.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/addtocart")
    public ResponseEntity<?> addToCart(@RequestBody CartRequestDTO cartRequestDTO){
        try{
            cartService.addToCart(cartRequestDTO);
            return ResponseEntity.ok().body(Map.of("message","added successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message","bad request"));
        }
    }
}

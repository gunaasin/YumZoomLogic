package com.guna.yumzoom.user;

public record UserSignInRequestDTO(
        String name,
        String username,
        String password,
        String email,
        String phone,
        String role
) {
}

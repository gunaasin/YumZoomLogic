package com.guna.yumzoom.user;

import lombok.Builder;

@Builder
public record UserSignUpRequestDTO(
        String email,
        String password
){
}

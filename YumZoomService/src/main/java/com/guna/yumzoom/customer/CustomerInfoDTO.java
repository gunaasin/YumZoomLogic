package com.guna.yumzoom.customer;
import lombok.Builder;

@Builder
public record CustomerInfoDTO(
        String name,
        String email,
        String phone
) {
}

package com.guna.yumzoom.customer;

import lombok.Builder;

@Builder
public record CustomerAddressResponseDTO(
        String name,
        String phone,
        String street,
        String city,
        String state,
        String pinCode
) {
}

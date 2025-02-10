package com.guna.yumzoom.address;

public record AddressRequestDTO(
        String street,
        String city,
        String state,
        String pinCode
) {
}

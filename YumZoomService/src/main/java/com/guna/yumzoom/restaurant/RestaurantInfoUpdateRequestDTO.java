package com.guna.yumzoom.restaurant;
import com.guna.yumzoom.address.AddressRequestDTO;

public record RestaurantInfoUpdateRequestDTO(
       String restaurantId,
       String name,
       String phone,
       String cusineType,
       boolean isActive,
       String image,
       AddressRequestDTO addressRequestDTO
) {
}

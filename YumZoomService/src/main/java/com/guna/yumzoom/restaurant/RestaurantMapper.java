package com.guna.yumzoom.restaurant;

import com.guna.yumzoom.security.EncryptionUtil;
import org.springframework.stereotype.Service;

@Service
public class RestaurantMapper {

     static RestaurantInformationDTO restaurantToDTO(Restaurant restaurant)  {
         String encryptedId;
         try {
            encryptedId = EncryptionUtil.encrypt(restaurant.getRestaurantId());
         } catch (Exception e) {
             throw new RuntimeException(e);
         }
         return RestaurantInformationDTO.builder()
                .restaurantId(encryptedId)
                .name(restaurant.getName())
                .image(restaurant.getImageData())
                .isActive(restaurant.isActive())
                .cusineType(restaurant.getCusineType())
                .rating(restaurant.getRating())
                .build();
    }


}

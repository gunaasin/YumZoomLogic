package com.guna.yumzoom.customer;

import com.guna.yumzoom.address.Address;
import com.guna.yumzoom.address.AddressRepo;
import com.guna.yumzoom.cart.Cart;
import com.guna.yumzoom.cart.CartRepo;
import com.guna.yumzoom.cartitem.CartItem;
import com.guna.yumzoom.cartitem.CartItemMapper;
import com.guna.yumzoom.cartitem.CartItemRepo;
import com.guna.yumzoom.cartitem.CartItemResponseDTO;
import com.guna.yumzoom.security.JwtService;
import com.guna.yumzoom.user.User;
import com.guna.yumzoom.user.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final JwtService jwtService;
    private final UserRepo userRepo;
    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;
    private final AddressRepo addressRepo;

    CustomerInfoDTO getCustomerInformation(String token){
        String email = jwtService.extractMailId(token);
        User user = userRepo.findByEmail(email);
        return CustomerInfoDTO.builder()
                .name(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }

    public List<?> getCartList(String token) {
        Cart cart = cartRepo.findByUserId(
                userRepo.findByEmail(jwtService.extractMailId(token)).getId());
        List<CartItemResponseDTO> cartItemList = cart.getCartItems()
                .stream()
                .map(CartItemMapper::convertCartItemToResponse)
                .toList();
        if(cartItemList.isEmpty()){
            return List.of("Cart is Empty");
        }
        return cartItemList;
    }

    @Transactional
    public void updateCart(int itemId, Integer quantity) {
        System.out.println(itemId);
        CartItem cartItem = cartItemRepo.findById(itemId).orElseThrow();
        if(quantity<1){
            cartItemRepo.deleteById(itemId);
        }else{
            cartItem.setQuantity(quantity);
            cartItem.setTotalPrice(cartItem.getFood().getPrice() * quantity);
            cartItemRepo.save(cartItem);
        }
    }

    @Transactional
    public void updateAddress(CustomerAddressRequestDTO dto) {
        String mailId = jwtService.extractMailId(dto.token());
        Address address = userRepo.findByEmail(mailId).getAddress();
        address.setName(dto.name());
        address.setPhone(dto.phone());
        address.setStreet(dto.street());
        address.setCity(dto.city());
        address.setState(dto.state());
        address.setPinCode(dto.pinCode());
        addressRepo.save(address);
    }

    public CustomerAddressResponseDTO  getAddress(String token) {
         String mailId = jwtService.extractMailId(token);
         Address address = userRepo.findByEmail(mailId).getAddress();
         return CustomerAddressResponseDTO.builder()
                 .name(address.getName())
                 .phone(address.getPhone())
                 .street(address.getStreet())
                 .city(address.getCity())
                 .state(address.getState())
                 .pinCode(address.getPinCode())
                 .build();
    }

}

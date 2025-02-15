package com.guna.yumzoom.payment;

import com.guna.yumzoom.cart.Cart;
import com.guna.yumzoom.cart.CartRepo;
import com.guna.yumzoom.cartitem.CartItemMapper;
import com.guna.yumzoom.cartitem.CartItemResponseDTO;
import com.guna.yumzoom.order.OrderService;
import com.guna.yumzoom.security.JwtService;
import com.guna.yumzoom.user.UserRepo;
import com.stripe.model.checkout.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final OrderService orderService;
    private final JwtService jwtService;
    private final UserRepo userRepo;
    private final CartRepo cartRepo;
    private final StripeService stripeService;

    public PaymentResponseDTO processPayment(PaymentRequestDTO paymentRequestDTO) {
        String mailId = jwtService.extractMailId(paymentRequestDTO.token());
        int userId  = userRepo.findByEmail(mailId).getId();
        Cart cart = cartRepo.findByUserId(userId);
        String paymentMode = paymentRequestDTO.paymentMode();
        List<CartItemResponseDTO> cartItemList  = cart.getCartItems()
                .stream()
                .map(CartItemMapper::convertCartItemToResponse)
                .toList();
        StringBuilder foodsName = new StringBuilder();
        Long totalPrice = 0L ;
        for(CartItemResponseDTO dto : cartItemList){
            foodsName.append(dto.foodName()).append(", ");
            totalPrice+= dto.price();
        }
        if(paymentRequestDTO.paymentMode().equals(PaymentModes.CASH_ON_DELIVERY.name())){
            orderService.placeOrder(cartItemList, userId, paymentMode);
            return PaymentResponseDTO.builder()
                    .message("Order Placed")
                    .sessionId("YumZoom_23465234635")
                    .sessionUrl("/process")
                    .status("ok")
                    .build();
        }else if (paymentRequestDTO.paymentMode().equals(PaymentModes.CARD.name())){
            StripeReqDTO dto = StripeReqDTO.builder()
                            .quantity(1)
                            .totalAmount(totalPrice + ((totalPrice*12)/100) + 5 + (totalPrice >500 ? 0 : 40))
                            .name(foodsName.toString())
                            .email(mailId)
                            .build();
           return stripeService.checkoutProduct(dto);

        }

        return null;
    }

    public void saveOrder(Session session) {
        int userId  = userRepo.findByEmail(session.getCustomerEmail()).getId();
        Cart cart = cartRepo.findByUserId(userId);
        String paymentMode = session.getPaymentMethodTypes().get(0).toUpperCase();
        List<CartItemResponseDTO> cartItemList  = cart.getCartItems()
                .stream()
                .map(CartItemMapper::convertCartItemToResponse)
                .toList();
        orderService.placeOrder(cartItemList, userId, paymentMode);
    }
}

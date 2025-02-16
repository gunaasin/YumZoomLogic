package com.guna.yumzoom.order;

import com.guna.yumzoom.cartitem.CartItemResponseDTO;
import com.guna.yumzoom.orderitem.OrderItem;

public class OrderMapper {
    static OrderItemRequestDTO cartItemToOrderItem(CartItemResponseDTO cartItemResponseDTO){
       return OrderItemRequestDTO.builder()
               .productId(cartItemResponseDTO.foodId())
               .quantity(cartItemResponseDTO.quantity())
               .build();
    }
    static OrderItemDTO convertOrderListDTO(OrderItem orderItem){
        return OrderItemDTO.builder()
                .foodImg(orderItem.getFood().getImagePath())
                .name(orderItem.getFood().getItemName())
                .originalAmount(orderItem.getFood().getPrice())
                .totalAmount(orderItem.getTotalPrice())
                .quantity(orderItem.getQuantity())
                .build();
    }
    static OrderDTO convertOrderDTO(Order order){
        return OrderDTO.builder()
                .orderId(order.getOrderId())
                .oderDate(order.getOrderDate())
                .totalAmount(order.getTotalAmount())
                .paymentMode(order.getPaymentMode())
                .status(order.getStatus())
                .orderItemDTOList(order.getOrderItems()
                        .stream()
                        .map(OrderMapper::convertOrderListDTO)
                        .toList())
                .build();

    }


}

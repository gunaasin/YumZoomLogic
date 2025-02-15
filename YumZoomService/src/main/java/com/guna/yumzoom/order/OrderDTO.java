package com.guna.yumzoom.order;

import lombok.Builder;

import java.util.List;

@Builder
public record OrderDTO(
    int totalAmount,
    String orderId,
    String paymentMode,
    String oderDate,
    List<OrderItemDTO> orderItemDTOList
) {
}

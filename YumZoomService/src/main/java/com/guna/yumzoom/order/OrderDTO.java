package com.guna.yumzoom.order;

import lombok.Builder;

import java.util.List;

@Builder
public record OrderDTO(
    int totalAmount,
    String orderId,
    String paymentMode,
    String oderDate,
    String status,
    List<OrderItemDTO> orderItemDTOList
) {
}

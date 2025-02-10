package com.guna.yumzoom.order;

import java.util.List;

public record OrderRequestDTO(
        int customerId,
        List<OrderItemRequestDTO> orderItems
) {
}

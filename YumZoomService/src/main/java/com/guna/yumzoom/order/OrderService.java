package com.guna.yumzoom.order;

import com.guna.yumzoom.menu.FoodRepo;
import com.guna.yumzoom.orderitem.OrderItem;
import com.guna.yumzoom.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final UserRepo userRepo;
    private final FoodRepo foodRepo;
    private final OrderRepo orderRepo;

    public void placeOrder(OrderRequestDTO orderRequestDTO) {
        Order order = Order.builder()
                .user(userRepo.findById(orderRequestDTO.customerId()).orElseThrow())
                .orderId(OrderIdGen.generateOrderIdHybrid())
                .status(OrderStatus.PENDING)
                .build();
        List<OrderItem> orderItems = new ArrayList<>();
        int price = 0;
        List<OrderItemRequestDTO> requestItem = orderRequestDTO.orderItems();
        for(OrderItemRequestDTO req:requestItem){
            var foodItem = foodRepo.findById(req.productId()).orElseThrow();
            OrderItem orderItem = OrderItem.builder()
                    .quantity(req.quantity())
                    .food(foodItem)
                    .totalPrice(foodItem.getPrice())
                    .build();
            price += foodItem.getPrice() * orderItem.getQuantity();
            orderItems.add(orderItem);
        }
        order.setTotalAmount(price);
        order.setOrderItems(orderItems);

         orderRepo.save(order);
    }

    public OrderStatus trackOrder(String orderID) {
        Order order = orderRepo.findByOrderId(orderID);
        return order.getStatus();
    }
}

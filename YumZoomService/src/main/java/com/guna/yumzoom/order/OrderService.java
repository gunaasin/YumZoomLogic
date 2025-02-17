package com.guna.yumzoom.order;

import com.guna.yumzoom.cart.CartRepo;
import com.guna.yumzoom.cartitem.CartItemRepo;
import com.guna.yumzoom.cartitem.CartItemResponseDTO;
import com.guna.yumzoom.menu.FoodRepo;
import com.guna.yumzoom.orderitem.OrderItem;
import com.guna.yumzoom.restaurantorder.RestaurantOrderService;
import com.guna.yumzoom.security.JwtService;
import com.guna.yumzoom.user.User;
import com.guna.yumzoom.user.UserRepo;
import com.guna.yumzoom.websocket.OrderWebSocketController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final UserRepo userRepo;
    private final FoodRepo foodRepo;
    private final OrderRepo orderRepo;
    private final JwtService jwtService;
    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;
    private final RestaurantOrderService restaurantOrderService;


    public void placeOrder(List<CartItemResponseDTO> cartItemResponseDTO , int userId , String paymentMode) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        List<OrderItemRequestDTO> orderItemRequestDTOS = cartItemResponseDTO
                .stream()
                .map(OrderMapper::cartItemToOrderItem)
                .toList();

        Order order = Order.builder()
                .user(userRepo.findById(userId).orElseThrow())
                .orderId(OrderIdGen.generateOrderIdHybrid())
                .orderDate(formattedDateTime)
                .paymentMode(paymentMode)
                .status(OrderStatus.CONFIRMED.name())
                .build();

        Order order1 = orderRepo.save(order);
        List<OrderItem> orderItems = new ArrayList<>();

        int price = 0;
        for(OrderItemRequestDTO req : orderItemRequestDTOS) {
            var foodItem = foodRepo.findById(req.productId()).orElseThrow();
            OrderItem orderItem = OrderItem.builder()
                    .order(order1)
                    .quantity(req.quantity())
                    .food(foodItem)
                    .totalPrice(foodItem.getPrice() * req.quantity())
                    .build();
            price += foodItem.getPrice() * req.quantity();
            orderItems.add(orderItem);
        }
        order1.setTotalAmount(price + ((price*12)/100) + 5 + (price > 500 ? 0  : 40)) ;
        order1.setOrderItems(orderItems);
        var order2 = orderRepo.save(order1);
        cartItemRepo.deleteAllCartItems(cartRepo.findByUserId(userId).getId());
        restaurantOrderService.saveOrders(order2);


    }

    public List<OrderDTO> getOrderList(String token) {
        List<Order> orders = userRepo.findByEmail(jwtService.extractMailId(token)).getOrders();
        return orders.stream()
                .map(OrderMapper::convertOrderDTO)
                .toList();
    }

}

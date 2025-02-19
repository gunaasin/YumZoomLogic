package com.guna.yumzoom.Agent;

import com.guna.yumzoom.address.Address;
import com.guna.yumzoom.agentOrders.AgentOrderRepo;
import com.guna.yumzoom.agentOrders.AgentOrders;
import com.guna.yumzoom.order.Order;
import com.guna.yumzoom.order.OrderRepo;
import com.guna.yumzoom.order.OrderStatus;
import com.guna.yumzoom.restaurant.RestaurantRepo;
import com.guna.yumzoom.restaurantorder.RestaurantOrder;
import com.guna.yumzoom.restaurantorder.RestaurantOrderRepo;
import com.guna.yumzoom.security.JwtService;
import com.guna.yumzoom.user.User;
import com.guna.yumzoom.user.UserRepo;
import com.guna.yumzoom.websocket.OrderWebSocketController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AgentService {
    private final JwtService jwtService;
    private final UserRepo userRepo;
    private final AgentRepo agentRepo;
    private final RestaurantOrderRepo restaurantOrderRepo;
    private final OrderWebSocketController orderWebSocketController;
    private final OrderRepo orderRepo;
    private final AgentOrderRepo agentOrderRepo;


    public AgentInfoDTO getAgentInfo(String token){
        User user = userRepo.findByEmail(jwtService.extractMailId(token));
        Agent agent = agentRepo.findByAgentId(user.getId());

        return AgentInfoDTO.builder()
                .email(user.getEmail())
                .status(agent.getAvailable())
                .name(user.getName())
//                .address(user.getAddress())
                .build();
    }

    public Object acceptOrder(AgentOrderAcceptDTO dto) {
        User user = userRepo.findByEmail(jwtService.extractMailId(dto.token()));
        User orderUser  = orderRepo.findByOrderId(dto.orderId()).getUser();
        Agent agent = agentRepo.findByAgentId(user.getId());

        Address userAddress = orderUser.getAddress();
        String deliveryLocation = orderUser.getName()+", "+userAddress.getStreet()+", "+userAddress.getCity();

        RestaurantOrder restaurantOrder = restaurantOrderRepo.findByOrderId(dto.orderId());
        Address restaurantAddress = restaurantOrder.getRestaurant().getUser().getAddress();
        String pickupLocation = restaurantOrder.getRestaurant().getName()+", "+restaurantAddress.getStreet()+", "+restaurantAddress.getCity();
        AgentOrders agentOrders = AgentOrders.builder()
                .agent(agent)
                .deliveryLocation(deliveryLocation)
                .customerNumber(user.getPhone())
                .pickupLocation(pickupLocation)
                .orderId(dto.orderId())
                .status(OrderStatus.OUT_FOR_DELIVERY.name())
                .build();
        List<AgentOrders> agentOrdersList = agent.getAgentOrdersList();
        agentOrdersList.add(agentOrders);
        agentRepo.save(agent);
        Order order = orderRepo.findByOrderId(dto.orderId());
        order.setStatus(OrderStatus.OUT_FOR_DELIVERY.name());
        orderRepo.save(order);
        orderWebSocketController.sendOrderStatus(dto.orderId(), OrderStatus.OUT_FOR_DELIVERY.name());
        return AgentOrderDispatchDTO.builder()
                .orderId(dto.orderId())
                .customerNumber(agentOrders.getCustomerNumber())
                .pickupLocation(pickupLocation)
                .deliveryLocation(deliveryLocation)
                .status(agentOrders.getStatus())
                .build();
    }

    public ResponseEntity<?> updateOrder(AgentOrderUpdateDTO dto) {
        var order = orderRepo.findByOrderId(dto.orderId());
        if(dto.status().equals(OrderStatus.OUT_FOR_DELIVERY.name())){
          order.setStatus(OrderStatus.DELIVERED.name());
          orderWebSocketController.sendOrderStatus(dto.orderId(), OrderStatus.DELIVERED.name());
          orderRepo.save(order);
          var agentOrder = agentOrderRepo.findByOrderId(dto.orderId());
          agentOrder.setStatus(OrderStatus.DELIVERED.name());
          agentOrderRepo.save(agentOrder);
          return ResponseEntity.ok().body(Map.of("message","Order Delivered"));
        }
        return ResponseEntity.ok().body(Map.of("message","Server Down"));
    }
}

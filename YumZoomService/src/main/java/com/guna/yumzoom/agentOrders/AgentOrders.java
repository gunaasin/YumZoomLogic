package com.guna.yumzoom.agentOrders;

import com.guna.yumzoom.Agent.Agent;
import com.guna.yumzoom.order.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class AgentOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String pickupLocation;
    private String deliveryLocation;
    private String status;
    private String customerNumber;
    private String orderId;

    @ManyToOne
    @JoinColumn(name = "agent_id",nullable = false)
    private Agent agent;
}

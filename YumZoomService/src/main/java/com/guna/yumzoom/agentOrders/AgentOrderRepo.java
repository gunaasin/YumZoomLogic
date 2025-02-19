package com.guna.yumzoom.agentOrders;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentOrderRepo extends JpaRepository<AgentOrders,Integer> {
    AgentOrders findByOrderId(String orderId);
}

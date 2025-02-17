package com.guna.yumzoom.Agent;

import com.guna.yumzoom.agentOrders.AgentOrders;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int agentId;
    private String agentEmail;
    private int agentTotalOrders;
    private String available;
    private Long agentTotalRevenue;

    @OneToMany(mappedBy = "agent", cascade = CascadeType.ALL , orphanRemoval = true)
    @ToString.Exclude
    private List<AgentOrders> agentOrdersList;
}

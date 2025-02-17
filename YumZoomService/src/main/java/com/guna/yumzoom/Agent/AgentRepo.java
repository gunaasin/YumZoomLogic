package com.guna.yumzoom.Agent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AgentRepo extends JpaRepository<Agent,Integer> {

    @Query("SELECT a FROM Agent a WHERE a.agentId = :id")
    Agent findByAgentId(@Param("id") Integer id);
}

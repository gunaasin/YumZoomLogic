package com.guna.yumzoom.Agent;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AgentController {
    private final AgentService agentService;

    @GetMapping("/get/agent/info")
    public ResponseEntity<?> getInfo(@RequestParam String token){
        return ResponseEntity.ok().body(agentService.getAgentInfo(token));
    }

    @PostMapping("/accept/order")
    public ResponseEntity<?> acceptOrder(@RequestBody AgentOrderAcceptDTO dto){
        return ResponseEntity.ok().body(agentService.acceptOrder(dto));
    }

    @PostMapping("/update/order")
    public ResponseEntity<?> updateOrder(@RequestBody AgentOrderUpdateDTO dto){
        return ResponseEntity.ok().body(agentService.updateOrder(dto));
    }
}

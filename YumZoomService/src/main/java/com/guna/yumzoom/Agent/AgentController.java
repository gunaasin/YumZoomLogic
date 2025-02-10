package com.guna.yumzoom.Agent;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AgentController {

    @GetMapping("/agent/getinfo")
    public String geeInto(){
        return null;
    }
}

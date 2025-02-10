package com.guna.yumzoom.customer;

import com.guna.yumzoom.order.OrderRequestDTO;
import com.guna.yumzoom.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CustomerController {

    private OrderService orderService;
    @PostMapping("/placeOrder")
    public ResponseEntity<?> placeOrder(
            @RequestBody OrderRequestDTO orderRequestDTO
    ){
        try{
            orderService.placeOrder(orderRequestDTO);
            return  ResponseEntity.ok().body(Map.of("message","success"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message","sumthing when wrong"));
        }
    }

    @GetMapping("/status")
    public ResponseEntity<?> trackOrder(@RequestParam String orderID){
        try{
            return  ResponseEntity.ok().body(Map.of("status",orderService.trackOrder(orderID)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message","sumthing when wrong"));
        }
    }
}

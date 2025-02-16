package com.guna.yumzoom.customer;

import com.guna.yumzoom.order.OrderService;
import com.guna.yumzoom.payment.PaymentRequestDTO;
import com.guna.yumzoom.payment.PaymentService;
import com.guna.yumzoom.websocket.OrderWebSocketController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final OrderService orderService;
    private final PaymentService paymentService;
    private final OrderWebSocketController orderWebSocketController;



    @PostMapping("/customer/placeOrder")
    public ResponseEntity<?> placeOrder(
            @RequestBody PaymentRequestDTO paymentRequestDTO
    ){
        try{
            return  ResponseEntity.ok().body(paymentService.processPayment(paymentRequestDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message","Something went wrong"));
        }
    }

    @GetMapping("/customer/orderlist")
    public ResponseEntity<?> getOrders(@RequestParam String token){
        return ResponseEntity.ok().body(orderService.getOrderList(token));
    }


    @GetMapping("/customer/getinfo")
    public ResponseEntity<?> getInformation(@RequestParam String token){
        System.out.println(token);
        return ResponseEntity.ok().body(customerService.getCustomerInformation(token));
    }

    @GetMapping("/customer/getCartList")
    public ResponseEntity<?> getCartList(@RequestParam String token){
        try{
            return ResponseEntity.ok().body(customerService.getCartList(token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message","Something went wrong"));
        }
    }

    @PostMapping("/customer/updatecart/{itemId}")
    public  ResponseEntity<?> updateCart(
            @PathVariable int itemId,
            @RequestBody Map<String , Integer> requestBody
    ){
        try{
            customerService.updateCart(itemId,requestBody.get("quantity"));
            return ResponseEntity.ok().body(List.of("message","Cart Updated :)"));
        } catch (Exception e) {
            return ResponseEntity.ok().body(List.of("message","Something went wrong"));
        }
    }

    @PostMapping("/customer/update/address")
    public ResponseEntity<?> updateAddress(@RequestBody CustomerAddressRequestDTO DTO){
        try {
            customerService.updateAddress(DTO);
            return ResponseEntity.ok().body(Map.of("message","Address updated"));
        } catch (Exception e) {
             return ResponseEntity.ok().body(Map.of("message","Enter valid One"));
        }
    }

    @GetMapping("/customer/get/address")
    public ResponseEntity<?> getAddress(@RequestParam String token){
        try {
            return ResponseEntity.ok().body(customerService.getAddress(token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message","Enter valid One"));
        }
    }

    @GetMapping("/test-websocket/{orderId}")
    public ResponseEntity<?> testWebSocket(@PathVariable String orderId) {
        orderWebSocketController.sendOrderStatus(orderId, "READY");
        return ResponseEntity.ok(List.of("WebSocket message sent to /topic/order/" + orderId));
    }


}

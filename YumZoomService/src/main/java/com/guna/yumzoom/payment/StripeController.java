package com.guna.yumzoom.payment;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class StripeController {
    private final PaymentService paymentService;

    @Value("${stripe.webhookSecret}")
    private String webhookSecret;

    @PostMapping("/stripe/webhook")
    public ResponseEntity<?> handleWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String signature) {
        Event event = null;
        try {
            event = Webhook.constructEvent(payload, signature, webhookSecret);
            System.out.println("Stripe Event received: " + event.getType());


            switch (event.getType()) {
                case "checkout.session.completed":
                    Session session = (Session) event.getData().getObject();
                    paymentService.saveOrder(session);
                    break;
                default:
                    System.out.println("Unhandled event type: " + event.getType());
                    break;
            }
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }
}

package com.guna.yumzoom.payment;

import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StripeService {


    @Value("${stripe.secretKey}")
    private String secretKey;

    @Value("${client.end-point}")
    private String CLI_END_POINT;


    public static Long convertDolor(Long inr) {
        return  inr * 100;
    }

    public PaymentResponseDTO checkoutProduct(StripeReqDTO dto){
        Stripe.apiKey=secretKey;
        var productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(dto.name()).build();

        var priceData = SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("INR")
                .setUnitAmount(convertDolor(dto.totalAmount()))
                .setProductData(productData)
                .build();

        var quantityData = SessionCreateParams.LineItem.builder()
                .setQuantity((long) dto.quantity())
                .setPriceData(priceData)
                .build();

        var param = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(CLI_END_POINT + "/process")
                .setCancelUrl(CLI_END_POINT + "/user")
                .setCustomerEmail(dto.email())
                .addLineItem(quantityData)
                .build();

        Session session = null;

        try {
            session = Session.create(param);
        } catch (StripeException e) {
            System.out.println(e.getMessage());
        }

        if (session == null) {
            return PaymentResponseDTO.builder()
                    .status("failed")
                    .message("Stripe session creation failed")
                    .sessionId(null)
                    .sessionUrl(null)
                    .build();
        }

        return PaymentResponseDTO.builder()
                .status("CART")
                .message("Payment Session Created")
                    .sessionId(session.getId())
                    .sessionUrl(session.getUrl())
                    .build();

    }
}

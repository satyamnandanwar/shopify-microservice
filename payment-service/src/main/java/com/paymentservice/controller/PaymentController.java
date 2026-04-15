package com.paymentservice.controller;

import com.paymentservice.service.PaymentService;
import com.stripe.exception.StripeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/checkout/{orderId}")
    public ResponseEntity<String> checkout(@PathVariable Long orderId) throws StripeException {

        String url = paymentService.createCheckoutSession(orderId);

//        return ResponseEntity.status(HttpStatus.FOUND)
//                .location(URI.create(url))
//                .build();
          return ResponseEntity.ok(url);
    }

    @GetMapping("/success")
    public ResponseEntity<Map<String, Object>> success(@RequestParam Long orderId) {

        // 🔥 UPDATE ORDER STATUS
        paymentService.markOrderPaid(orderId);

        return ResponseEntity.ok(
                Map.of(
                        "message", "Payment successful",
                        "orderId", orderId,
                        "status", "PAID"
                )
        );
    }

    @GetMapping("/cancel")
    public ResponseEntity<String> cancel(@RequestParam Long orderId) {
        return ResponseEntity.ok("Payment cancelled for Order #" + orderId);
    }
}
package com.paymentservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "order-service", url = "http://localhost:8084")
public interface OrderClient {

    @PutMapping("/api/v1/order/{orderId}/status")
    void updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String status
    );
}
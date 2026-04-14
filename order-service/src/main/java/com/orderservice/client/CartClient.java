package com.orderservice.client;

import com.orderservice.dto.CartResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "cart-service", url = "http://localhost:8083")
public interface CartClient {

    @GetMapping("/api/v1/cart")
    CartResponse getCart(@RequestHeader("X-CART-ID") String uuid);

    @DeleteMapping("/api/v1/cart/{uuid}/clear")
    void clearCart(@PathVariable String uuid);
}

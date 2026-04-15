package com.orderservice.controller;

import com.orderservice.entity.Order;
import com.orderservice.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PutMapping("/{orderId}/status")
    public void updateStatus(
            @PathVariable Long orderId,
            @RequestParam String status
    ) {
        orderService.updateOrderStatus(orderId, status);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Map<String, Object>> getOrder(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(
                Map.of(
                        "id",          order.getId(),
                        "totalAmount", order.getTotalAmount(),
                        "status",      order.getStatus()
                )
        );
    }

    @PostMapping("/place")
    public ResponseEntity<Map<String, Object>> placeOrder(
            @RequestHeader("X-CART-ID") String uuid
    ) {

        Order order = orderService.createOrder(uuid);

        return ResponseEntity.ok(
                Map.of(
                        "message", "Order placed successfully",
                        "orderId", order.getId(),
                        "totalAmount", order.getTotalAmount()
                )
        );
    }
}
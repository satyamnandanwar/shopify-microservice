package com.orderservice.service;

import com.orderservice.client.CartClient;
import com.orderservice.dto.CartItemResponse;
import com.orderservice.dto.CartResponse;
import com.orderservice.entity.Order;
import com.orderservice.entity.OrderItem;
import com.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartClient cartClient;

    public OrderService(OrderRepository orderRepository, CartClient cartClient) {
        this.orderRepository = orderRepository;
        this.cartClient = cartClient;
    }

    public void updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);

        orderRepository.save(order);
    }

    @Transactional
    public Order createOrder(String uuid) {

        // 🔹 Step 1: Fetch cart using Feign
        CartResponse cart = cartClient.getCart(uuid);

        // 🔹 Step 2: Validate cart
        if (cart == null || cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // 🔹 Step 3: Create Order
        Order order = new Order();
        order.setCartUuid(uuid);
        order.setStatus("CREATED");

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        // 🔹 Step 4: Convert CartItems → OrderItems
        for (CartItemResponse item : cart.getCartItems()) {

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(item.getProductId());
            orderItem.setBrandId(item.getBrandId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getPrice());

            // 🔥 IMPORTANT: set relationship
            orderItem.setOrder(order);

            orderItems.add(orderItem);

            // 🔹 Step 5: Calculate total
            totalAmount = totalAmount.add(
                    item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
            );
        }

        // 🔹 Step 6: Attach items to order
        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);

        // 🔹 Step 7: Save Order
        Order savedOrder = orderRepository.save(order);

        // 🔹 Step 8: Clear Cart (via Feign)
        cartClient.clearCart(uuid);

        return savedOrder;
    }
}
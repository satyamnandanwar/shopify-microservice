package com.paymentservice.service;

import com.paymentservice.client.OrderClient;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service
public class PaymentService {

    private final OrderClient orderClient;

    public PaymentService(OrderClient orderClient) {
        this.orderClient = orderClient;
    }

    public void markOrderPaid(Long orderId) {
        orderClient.updateOrderStatus(orderId, "PAID");
    }

    public String createCheckoutSession(Long orderId) throws StripeException {

        // 1. Fetch real order total from order-service
        Map<String, Object> order = orderClient.getOrder(orderId);
        BigDecimal totalAmount = new BigDecimal(order.get("totalAmount").toString());

        // 2. Convert to Stripe smallest unit (cents/paise)
        Long unitAmount = totalAmount
                .multiply(BigDecimal.valueOf(100))
                .setScale(0, RoundingMode.HALF_UP)
                .longValueExact();

        // 3. Build checkout session with dynamic amount
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8085/api/v1/payment/success?orderId=" + orderId)
                .setCancelUrl("http://localhost:8085/api/v1/payment/cancel?orderId=" + orderId)
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("usd")
                                                .setUnitAmount(unitAmount)  // ✅ was 5000L
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData
                                                                .ProductData.builder()
                                                                .setName("Order #" + orderId)
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();

        Session session = Session.create(params);
        return session.getUrl();
    }
}
package com.paymentservice.service;

import com.paymentservice.client.OrderClient;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Service;

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
                                                .setUnitAmount(5000L)
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
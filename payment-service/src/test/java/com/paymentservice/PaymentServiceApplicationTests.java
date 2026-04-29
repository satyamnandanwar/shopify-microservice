package com.paymentservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest(properties = "stripe.secret.key=sk_test_dummy")
class PaymentServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}

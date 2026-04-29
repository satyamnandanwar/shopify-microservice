package com.apigateway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "jwt.secret=dGVzdC1qd3Qtc2VjcmV0LWZvci1oczI1Ni10ZXN0cy0zMi1ieXRlcw==")
class ApiGatewayApplicationTests {

    @Test
    void contextLoads() {
    }

}

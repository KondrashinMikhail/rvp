package ru.ulstu.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GatewayApplication {
    public static final String CORRELATION_ID = "correlation-id";

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}

package ru.ulstu.gateway.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@FeignClient(name = "token", url = "http://localhost:8082/reports")
public interface TokenFeign {
    @GetMapping("/token/{service}/get")
    String getToken(@RequestHeader("correlation-id") String correlationId, @PathVariable("service") String service);

    @GetMapping("/token/is-valid")
    Boolean isTokenValid(@RequestHeader(AUTHORIZATION) String token);
}

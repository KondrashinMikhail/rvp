package ru.ulstu.gateway.filter;

import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static ru.ulstu.gateway.GatewayApplication.CORRELATION_ID;

@Component
@Log4j2
public class CorrelationTrackingPreFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders header = request.getHeaders();

        if (hasCorrelationId(header)) {
            log.info(String.format("----------Обнаружена корреляция запроса %s", header.get(CORRELATION_ID)));
            header.set(CORRELATION_ID, "");
            log.info("----------Сброшена корреляция");
        }
        else {
            String correlation = generateCorrelationId();
            request = exchange
                    .getRequest()
                    .mutate()
                    .header(CORRELATION_ID, correlation)
                    .build();
            log.info(String.format("----------Определена корреляция запроса %s", correlation));
            return chain.filter(exchange.mutate().request(request).build());
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return FilterOrderType.PRE.getOrder();
    }

    private boolean hasCorrelationId(HttpHeaders header) {
        return header.containsKey(CORRELATION_ID);
    }

    private String generateCorrelationId() {
        return UUID.randomUUID().toString();
    }
}

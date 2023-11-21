package ru.ulstu.gateway.filter;

import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static ru.ulstu.gateway.GatewayApplication.CORRELATION_ID;

@Component
@Log4j2
public class CorrelationTrackingPostFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders header = exchange.getRequest().getHeaders();
        String correlation = getCorrelationId(header);
        exchange.getResponse().getHeaders().add(CORRELATION_ID, correlation);
        log.info(String.format("----------Вставлена корреляция %s в запрос", correlation));
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return FilterOrderType.POST.getOrder();
    }

    private String getCorrelationId(HttpHeaders header) {
        return Objects.requireNonNull(header.get(CORRELATION_ID)).iterator().next();
    }
}

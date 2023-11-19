package ru.ulstu.reports.rabbitmq.producer;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static ru.ulstu.reports.rabbitmq.config.RabbitConfig.EXCHANGE;

@Service
@AllArgsConstructor
public class RabbitMQProducerServiceImpl implements RabbitMQProducerService {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendMessage(String routingKey, String message) {
        rabbitTemplate.convertAndSend(EXCHANGE, routingKey, message);
    }
}

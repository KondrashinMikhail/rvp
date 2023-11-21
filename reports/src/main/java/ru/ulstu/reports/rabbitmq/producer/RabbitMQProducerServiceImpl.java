package ru.ulstu.reports.rabbitmq.producer;

import lombok.AllArgsConstructor;
import org.json.JSONObject;
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

    @Override
    public void sendFile(String routingKey, JSONObject file) {
        rabbitTemplate.convertAndSend(EXCHANGE, routingKey, file.toString());
    }
}

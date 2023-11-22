package ru.ulstu.reports.services.implementations;

import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.ulstu.reports.services.interfaces.RabbitMQProducerService;

import static ru.ulstu.reports.configs.RabbitConfig.EXCHANGE;

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

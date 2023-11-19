package ru.ulstu.reports.rabbitmq.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.ulstu.reports.models.DTO.RabbitMessageDTO;
import ru.ulstu.reports.rabbitmq.producer.RabbitMQProducerService;

@RestController
@AllArgsConstructor
public class RabbitController {
    private final RabbitMQProducerService rabbitMQProducerService;

    @GetMapping("/send")
    public void sendMessageToRabbit(@RequestBody RabbitMessageDTO rabbitMessageDTO) {
        rabbitMQProducerService.sendMessage(rabbitMessageDTO.getRoutingKey(), rabbitMessageDTO.getMessage());
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "OK";
    }
}

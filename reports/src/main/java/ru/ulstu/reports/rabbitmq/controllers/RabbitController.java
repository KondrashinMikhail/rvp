package ru.ulstu.reports.rabbitmq.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ulstu.reports.models.DTO.RabbitMessageDTO;
import ru.ulstu.reports.rabbitmq.producer.RabbitMQProducerService;
import ru.ulstu.reports.services.ReportsService;

import static ru.ulstu.reports.rabbitmq.config.RabbitConfig.ROUTING_KEY;

@RestController
@RequestMapping("/rabbitmq")
@AllArgsConstructor
public class RabbitController {
    private final RabbitMQProducerService rabbitMQProducerService;
    private final ReportsService reportsService;

    @GetMapping("/send")
    public void sendMessageToRabbit(@RequestBody RabbitMessageDTO rabbitMessageDTO) {
        rabbitMQProducerService.sendMessage(rabbitMessageDTO.getRoutingKey(), rabbitMessageDTO.getMessage());
    }

    @GetMapping("/send/report/all")
    public void sendAllDataToRabbit() {
        rabbitMQProducerService.sendMessage(ROUTING_KEY, reportsService.getJSONAll().toString());
    }

    @GetMapping("/send/report/active")
    public void sendActiveDataToRabbit() {
        rabbitMQProducerService.sendMessage(ROUTING_KEY, reportsService.getJSONByActive(true).toString());
    }

    @GetMapping("/send/report/disabled")
    public void sendDisabledDataToRabbit() {
        rabbitMQProducerService.sendMessage(ROUTING_KEY, reportsService.getJSONByActive(false).toString());
    }
}

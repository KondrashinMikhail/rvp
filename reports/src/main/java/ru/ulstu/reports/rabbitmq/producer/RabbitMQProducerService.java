package ru.ulstu.reports.rabbitmq.producer;

import org.json.JSONObject;

public interface RabbitMQProducerService {
    void sendMessage(String routingKey, String message);
    void sendFile(String routingKey, JSONObject file);
}

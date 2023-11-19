package ru.ulstu.reports.rabbitmq.producer;

public interface RabbitMQProducerService {
    void sendMessage(String routingKey, String message);
}

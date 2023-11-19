package ru.ulstu.filestorage.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static ru.ulstu.filestorage.rabbitmq.config.RabbitConfig.QUEUE;


@Component
@EnableRabbit
public class RabbitMQConsumer {
    @RabbitListener(queues = QUEUE)
    public void processMyQueue(String message) {




        System.out.printf("Received from myQueue : %s %n", new String(message.getBytes()));
    }
}

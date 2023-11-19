package ru.ulstu.filestorage.rabbitmq.consumer;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.ulstu.filestorage.minio.MinioAdapter;

import static ru.ulstu.filestorage.controllers.FilestorageController.defaultBucketName;
import static ru.ulstu.filestorage.rabbitmq.config.RabbitConfig.QUEUE;

@Component
@EnableRabbit
@AllArgsConstructor
public class RabbitMQConsumer {
    private final MinioAdapter minioAdapter;

    @SneakyThrows
    @RabbitListener(queues = QUEUE)
    public void processMyQueue(String jsonObject) {
        JSONObject object = new JSONObject(jsonObject);
        minioAdapter.uploadFile(
                defaultBucketName,
                object.getString("filename"),
                java.util.Base64.getDecoder().decode(object.getString("data")));
    }
}

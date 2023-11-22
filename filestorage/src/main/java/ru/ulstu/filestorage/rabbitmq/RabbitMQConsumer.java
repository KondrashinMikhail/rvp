package ru.ulstu.filestorage.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.ulstu.filestorage.minio.MinioAdapter;

import static ru.ulstu.filestorage.controllers.FilestorageController.defaultBucketName;
import static ru.ulstu.filestorage.config.RabbitConfig.QUEUE;

@Component
@EnableRabbit
@AllArgsConstructor
@Log4j2
public class RabbitMQConsumer {
    private final MinioAdapter minioAdapter;

    @SneakyThrows
    @RabbitListener(queues = QUEUE)
    public void processMyQueue(String jsonObject) {
        log.info(String.format("Взят объект из очереди %s: %s", QUEUE, jsonObject));
        log.info(String.format("Корреляция предшествующего запроса: %s", new JSONObject(jsonObject).get("correlation-id")));
        JSONObject object = new JSONObject(jsonObject);
        minioAdapter.uploadFile(
                defaultBucketName,
                object.getString("filename"),
                java.util.Base64.getDecoder().decode(object.getString("data")));
    }
}

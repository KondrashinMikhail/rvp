package ru.ulstu.filestorage.minio;

import io.minio.*;
import io.minio.messages.Bucket;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class MinioAdapter {
    private final MinioClient minioClient;

    @SneakyThrows
    public List<Bucket> getAllBuckets() {
        List<Bucket> list = minioClient.listBuckets();
        log.info("Получены все корзины");
        return list;
    }

    @SneakyThrows
    public void createBucket(String bucketName) {
        boolean isExists = minioClient.bucketExists(
                BucketExistsArgs.builder()
                        .bucket(bucketName)
                        .build());
        if (!isExists)
            minioClient.makeBucket(
                    MakeBucketArgs.builder()
                            .bucket(bucketName)
                            .build());
        log.info(String.format("Создана корзина %s", bucketName));
    }

    @SneakyThrows
    public boolean uploadFile(String bucket, String name, byte[] content) {
        File file = new File(name);
        file.canWrite();
        file.canRead();

        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(content);

        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucket)
                .object(name)
                .stream(new FileInputStream(name), file.length(), -1)
                .build());

        log.info(String.format("В корзину %s загружен файл %s", bucket, name));
        return true;
    }

    @SneakyThrows
    public byte[] getFile(String bucket, String key) {
        byte[] data = IOUtils.toByteArray(
                minioClient.getObject(
                        GetObjectArgs.builder()
                                .bucket(bucket)
                                .object(key)
                                .build()));
        log.info(String.format("Из корзины %s получен файл %s", bucket, key));
        return data;
    }
}

package ru.ulstu.filestorage.controllers;

import io.minio.messages.Bucket;
import lombok.SneakyThrows;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ulstu.filestorage.DTO.FileDTO;
import ru.ulstu.filestorage.minio.MinioAdapter;

import java.util.List;

@RestController
@RequestMapping("/filestorage")
public class FilestorageController {
    private final MinioAdapter minioAdapter;
    public static final String defaultBucketName = "default";

    public FilestorageController(MinioAdapter minioAdapter) {
        this.minioAdapter = minioAdapter;
        List<String> buckets = minioAdapter.getAllBuckets().stream().map(Bucket::name).toList();
        if (buckets.isEmpty())
            minioAdapter.createBucket(defaultBucketName);
    }

    @GetMapping("/get/all")
    public List<String> getAll() {
        return minioAdapter.getAllBuckets().stream().map(Bucket::name).toList();
    }

    @SneakyThrows
    @PostMapping("/upload")
    public String uploadFile(@RequestPart("file") MultipartFile file) {
        if (minioAdapter.uploadFile(defaultBucketName, file.getOriginalFilename(), file.getBytes()))
            return file.getOriginalFilename();
        else return "File already exists!!!";
    }

    @SneakyThrows
    @PostMapping("/upload/bytes")
    public String uploadFile(@RequestBody FileDTO file) {
        if (minioAdapter.uploadFile(defaultBucketName, file.getFilename(), file.getBytes()))
            return file.getFilename();
        else return "File already exists!!!";
    }

    @SneakyThrows
    @GetMapping("/download")
    public ResponseEntity<?> downloadFile(@RequestPart("file") String file) {
        byte[] data = minioAdapter.getFile(defaultBucketName, file);
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + file + "\"")
                .body(resource);
    }
}

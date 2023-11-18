package ru.ulstu.reports.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.ulstu.reports.models.DTO.FileDTO;

@FeignClient(name = "filestorage", url = "${filestorage.client.url}")
public interface FilestorageClient {
//    @PostMapping("/upload")
//    String uploadFile(@RequestPart("file") MultipartFile file);

    @PostMapping("/upload/bytes")
    public String uploadFile(@RequestBody FileDTO file);
}

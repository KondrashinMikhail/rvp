package ru.ulstu.supplier.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.ulstu.supplier.models.DTO.SupplierNumeratedDTO;

import java.util.List;

import static ru.ulstu.supplier.controllers.SupplierController.CORRELATION_ID;

@FeignClient(name = "reports", url = "${reports.client.url}")
public interface ReportsClient {
    @GetMapping("/get/all")
    List<SupplierNumeratedDTO> getAllReport(@RequestHeader(CORRELATION_ID) String correlationId);

    @GetMapping("/get/active")
    List<SupplierNumeratedDTO> getActiveReport(@RequestHeader(CORRELATION_ID) String correlationId);

    @GetMapping("/get/disabled")
    List<SupplierNumeratedDTO> getDisabledReport(@RequestHeader(CORRELATION_ID) String correlationId);
}

package ru.ulstu.supplier.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import ru.ulstu.supplier.models.DTO.SupplierNumeratedDTO;

import java.util.List;

@FeignClient(name = "reports", url = "${reports.client.url}")
public interface ReportsClient {
    @GetMapping("/get/all")
    List<SupplierNumeratedDTO> getAllReport();

    @GetMapping("/get/active")
    List<SupplierNumeratedDTO> getActiveReport();

    @GetMapping("/get/disabled")
    List<SupplierNumeratedDTO> getDisabledReport();
}

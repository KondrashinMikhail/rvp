package ru.ulstu.supplier.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.ulstu.supplier.models.DTO.SupplierNumeratedDTO;

import java.util.List;

import static ru.ulstu.supplier.controllers.SupplierController.CORRELATION_ID;

@FeignClient(name = "reports", url = "${reports.client.url}")
public interface ReportsClient {
    @GetMapping("/get/all")
    List<SupplierNumeratedDTO> getAllReport(@RequestHeader(CORRELATION_ID) String correlationId,
                                            @RequestHeader("Authorization") String token);

    @GetMapping("/get/active")
    List<SupplierNumeratedDTO> getActiveReport(@RequestHeader(CORRELATION_ID) String correlationId,
                                               @RequestHeader("Authorization") String token);

    @GetMapping("/get/disabled")
    List<SupplierNumeratedDTO> getDisabledReport(@RequestHeader(CORRELATION_ID) String correlationId,
                                                 @RequestHeader("Authorization") String token);

    @GetMapping("/token/{service}")
    String getToken(@RequestHeader(CORRELATION_ID) String correlationId, @PathVariable("service") String service);
}

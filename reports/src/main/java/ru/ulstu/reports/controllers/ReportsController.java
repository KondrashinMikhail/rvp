package ru.ulstu.reports.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import ru.ulstu.reports.models.DTO.SupplierNumeratedDTO;
import ru.ulstu.reports.services.ReportsService;

import java.util.List;

@RestController
@RequestMapping("/reports")
@AllArgsConstructor
@Log4j2
public class ReportsController {
    public static final String CORRELATION_ID = "correlation-id";
    private final ReportsService service;

    @GetMapping("/get/active")
    public List<SupplierNumeratedDTO> getActiveSuppliers(@RequestHeader(CORRELATION_ID) String correlationId) {
        return service.getByActive(true, getNewCorrelation("get-disabled", correlationId));
    }

    @GetMapping("/get/disabled")
    public List<SupplierNumeratedDTO> getDisabledSuppliers(@RequestHeader(CORRELATION_ID) String correlationId) {
        return service.getByActive(false, getNewCorrelation("get-disabled", correlationId));
    }

    @GetMapping("get/all")
    public List<SupplierNumeratedDTO> getAll(@RequestHeader(CORRELATION_ID) String correlationId) {
        return service.getAll(getNewCorrelation("get-all", correlationId));
    }

    private String getNewCorrelation(String method, String correlationId) {
        HttpHeaders headers = new HttpHeaders();
        String newCorrelation = String.format("%s/%s->%s", correlationId, "reports", method);
        headers.set(CORRELATION_ID, newCorrelation);
        log.info(String.format("Дополнена корреляция: %s/%s->%s", correlationId, "reports", method));
//        return new HttpEntity<>(headers);
        return newCorrelation;
    }
}

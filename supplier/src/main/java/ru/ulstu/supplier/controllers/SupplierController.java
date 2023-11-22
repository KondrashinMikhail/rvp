package ru.ulstu.supplier.controllers;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import ru.ulstu.supplier.feign.ReportsClient;
import ru.ulstu.supplier.models.DTO.SupplierCutDTO;
import ru.ulstu.supplier.models.DTO.SupplierDTO;
import ru.ulstu.supplier.models.DTO.SupplierNumeratedDTO;
import ru.ulstu.supplier.services.SupplierService;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/supplier")
@AllArgsConstructor
@Log4j2
public class SupplierController {
    public static final String CORRELATION_ID = "correlation-id";
    private final SupplierService service;
    private final ReportsClient reportsClient;

    @PostMapping("/save")
    public SupplierDTO save(@RequestBody SupplierDTO supplier,
                            @RequestHeader(CORRELATION_ID) String correlationId) {
        log.info(String.format("Получен запрос с корреляцией %s:", correlationId));
        return service.addNew(supplier);
    }

    @GetMapping("/get/all")
    public List<SupplierDTO> getAll(@RequestHeader(CORRELATION_ID) String correlationId) {
        log.info(String.format("Получен запрос с корреляцией %s:", correlationId));
        return service.getAll();
    }

    @GetMapping("/get-ogrn")
    public List<SupplierCutDTO> getAllOGRN(@RequestHeader(CORRELATION_ID) String correlationId) {
        log.info(String.format("Получен запрос с корреляцией %s:", correlationId));
        return service.getOGRN();
    }

    @GetMapping("/get/active")
    public List<SupplierDTO> getActiveSuppliers(@RequestHeader(CORRELATION_ID) String correlationId) {
        log.info(String.format("Получен запрос с корреляцией %s:", correlationId));
        return service.getByActive(true);
    }

    @GetMapping("/get/disabled")
    public List<SupplierDTO> getDisabledSuppliers(@RequestHeader(CORRELATION_ID) String correlationId) {
        log.info(String.format("Получен запрос с корреляцией %s:", correlationId));
        return service.getByActive(false);
    }

    @PatchMapping("/update")
    public SupplierDTO update(@RequestBody SupplierDTO supplier,
                              @RequestHeader(CORRELATION_ID) String correlationId) {
        log.info(String.format("Получен запрос с корреляцией %s:", correlationId));
        return service.update(supplier.getId(), supplier.getName(), supplier.getIsActive());
    }

    @DeleteMapping("/delete/{id}")
    public SupplierDTO delete(@PathVariable Long id,
                              @RequestHeader(CORRELATION_ID) String correlationId) {
        log.info(String.format("Получен запрос с корреляцией %s:", correlationId));
        return service.delete(id);
    }


    @SneakyThrows
    @GetMapping("/get/all/report")
    public List<SupplierNumeratedDTO> getAllReport(@RequestHeader(CORRELATION_ID) String correlationId) {
        String[] response = getTokenForReports(correlationId).split("///");
        return service.getAllReportAsync(
                        getNewCorrelation("get-all-report", response[0]),
                        response[1])
                .get(5, TimeUnit.SECONDS);
    }

    @SneakyThrows
    @GetMapping("/get/active/report")
    public List<SupplierNumeratedDTO> getActiveReport(@RequestHeader(CORRELATION_ID) String correlationId) {
        String[] response = getTokenForReports(correlationId).split("///");
        return service.getByActiveReportAsync(
                        true,
                        getNewCorrelation("get-active-report", response[0]),
                        response[1])
                .get(5, TimeUnit.SECONDS);
    }

    @SneakyThrows
    @GetMapping("/get/disabled/report")
    public List<SupplierNumeratedDTO> getDisabledReport(@RequestHeader(CORRELATION_ID) String correlationId) {
        String[] response = getTokenForReports(correlationId).split("///");
        return service.getByActiveReportAsync(
                        false,
                        getNewCorrelation("get-disabled-report", response[0]),
                        response[1])
                .get(5, TimeUnit.SECONDS);
    }

    private String getNewCorrelation(String method, String correlationId) {
        String newCorrelation = String.format("%s/%s->%s", correlationId, "supplier", method);
        log.info(String.format("Дополнена корреляция: %s/%s->%s", correlationId, "supplier", method));
        return newCorrelation;
    }

    private String getTokenForReports(String correlationId) {
        return reportsClient.getToken(correlationId, "supplier");
    }
}

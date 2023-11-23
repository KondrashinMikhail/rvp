package ru.ulstu.reports.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ulstu.reports.models.DTO.SupplierNumeratedDTO;
import ru.ulstu.reports.services.interfaces.ReportsService;
import ru.ulstu.reports.utils.JwtTokenUtils;

import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/reports")
@AllArgsConstructor
@Log4j2
@SecurityRequirement(name = AUTHORIZATION)
public class ReportsController {
    public static final String CORRELATION_ID = "correlation-id";
    private final ReportsService service;
    private final JwtTokenUtils tokenUtils;

    @SneakyThrows
    @GetMapping("/get/active")
    public List<SupplierNumeratedDTO> getActiveSuppliers(@RequestHeader(CORRELATION_ID) String correlationId,
                                                         @RequestHeader(AUTHORIZATION) String token) {
        if (!tokenUtils.getServiceGeneratedBy(token)) throw new Exception("Неправильный токен");
        return service.getByActive(true, getNewCorrelation("get-disabled", correlationId));
    }

    @SneakyThrows
    @GetMapping("/get/disabled")
    public List<SupplierNumeratedDTO> getDisabledSuppliers(@RequestHeader(CORRELATION_ID) String correlationId,
                                                           @RequestHeader(AUTHORIZATION) String token) {
        if (!tokenUtils.getServiceGeneratedBy(token)) throw new Exception("Неправильный токен");
        return service.getByActive(false, getNewCorrelation("get-disabled", correlationId));
    }

    @SneakyThrows
    @GetMapping("/get/all")
    public List<SupplierNumeratedDTO> getAll(@RequestHeader(CORRELATION_ID) String correlationId,
                                             @RequestHeader(AUTHORIZATION) String token) {
        if (!tokenUtils.getServiceGeneratedBy(token)) throw new Exception("Неправильный токен");
        return service.getAll(getNewCorrelation("get-all", correlationId));
    }

    @GetMapping("/token/{serviceString}/get")
    public String getToken(@RequestHeader(CORRELATION_ID) String correlationId,
                           @PathVariable("serviceString") String serviceString) {
        if (!Objects.equals(serviceString, "supplier")) return null;
        String newCorrelationId = getNewCorrelation("get-token", correlationId);
        return String.format("%s///%s", newCorrelationId, tokenUtils.generateToken());
    }

    @GetMapping("/token/is-valid/{token}")
    public Boolean isTokenValid(@PathVariable("token") String token) {
        return (tokenUtils.getServiceGeneratedBy(token));
    }

    private String getNewCorrelation(String method, String correlationId) {
        String newCorrelation = String.format("%s/%s->%s", correlationId, "reports", method);
        log.info(String.format("Дополнена корреляция: %s/%s->%s", correlationId, "reports", method));
        return newCorrelation;
    }
}

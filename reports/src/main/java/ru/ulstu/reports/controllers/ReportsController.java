package ru.ulstu.reports.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.ulstu.reports.models.DTO.SupplierNumeratedDTO;
import ru.ulstu.reports.services.ReportsService;

import java.util.List;

@RestController
@RequestMapping("/reports")
@AllArgsConstructor
public class ReportsController {
    private final ReportsService service;

    @GetMapping("/get/active")
    public List<SupplierNumeratedDTO> getActiveSuppliers() {
        return service.getByActive(true);
    }

    @GetMapping("/get/disabled")
    public List<SupplierNumeratedDTO> getDisabledSuppliers() {
        return service.getByActive(false);
    }

    @GetMapping("get/all")
    public List<SupplierNumeratedDTO> getAll() {
        return service.getAll();
    }
}

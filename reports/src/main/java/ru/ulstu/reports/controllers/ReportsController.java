package ru.ulstu.reports.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.ulstu.reports.models.DTO.SupplierDTO;
import ru.ulstu.reports.services.ReposrtsService;

import java.util.List;

@RestController
@RequestMapping("/reports")
@AllArgsConstructor
public class ReportsController {
    private final ReposrtsService service;

    @GetMapping("/get/active")
    public List<SupplierDTO> getActiveSuppliers() {
        return service.getByActive(true);
    }

    @GetMapping("/get/disabled")
    public List<SupplierDTO> getDisabledSuppliers() {
        return service.getByActive(false);
    }
}

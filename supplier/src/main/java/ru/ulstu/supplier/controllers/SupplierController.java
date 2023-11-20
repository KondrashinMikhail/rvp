package ru.ulstu.supplier.controllers;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import ru.ulstu.supplier.models.DTO.SupplierCutDTO;
import ru.ulstu.supplier.models.DTO.SupplierDTO;
import ru.ulstu.supplier.models.DTO.SupplierNumeratedDTO;
import ru.ulstu.supplier.services.SupplierService;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/supplier")
@AllArgsConstructor
public class SupplierController {
    private final SupplierService service;
//    private final ReportsClient reportsClient;

    @PostMapping("/save")
    public SupplierDTO save(@RequestBody SupplierDTO supplier) {
        return service.addNew(supplier);
    }

    @GetMapping("/get/all")
    public List<SupplierDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/get-ogrn")
    public List<SupplierCutDTO> getAllOGRN() {
        return service.getOGRN();
    }

    @GetMapping("/get/active")
    public List<SupplierDTO> getActiveSuppliers() {
        return service.getByActive(true);
    }

    @GetMapping("/get/disabled")
    public List<SupplierDTO> getDisabledSuppliers() {
        return service.getByActive(false);
    }

    @PatchMapping("/update")
    public SupplierDTO update(@RequestBody SupplierDTO supplier) {
        return service.update(supplier.getId(), supplier.getName(), supplier.getIsActive());
    }

    @DeleteMapping("/delete/{id}")
    public SupplierDTO delete(@PathVariable Long id) {
        return service.delete(id);
    }


    @SneakyThrows
    @GetMapping("/get/all/report")
    public List<SupplierNumeratedDTO> getAllReport() {
        return service.getAllReportAsync().get(5, TimeUnit.SECONDS);
    }

    @SneakyThrows
    @GetMapping("/get/active/report")
    public List<SupplierNumeratedDTO> getActiveReport() {
        return service.getByActiveReportAsync(true).get(5, TimeUnit.SECONDS);
    }

    @SneakyThrows
    @GetMapping("/get/disabled/report")
    public List<SupplierNumeratedDTO> getADisabledReport() {
        return service.getByActiveReportAsync(false).get(5, TimeUnit.SECONDS);
    }
}

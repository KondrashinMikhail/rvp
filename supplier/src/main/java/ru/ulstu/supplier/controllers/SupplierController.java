package ru.ulstu.supplier.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.ulstu.supplier.models.DTO.SupplierCutDTO;
import ru.ulstu.supplier.models.DTO.SupplierDTO;
import ru.ulstu.supplier.services.SupplierService;

import java.util.List;

@RestController
@RequestMapping("/supplier")
@AllArgsConstructor
public class SupplierController {
    private final SupplierService service;

    @PostMapping("/save")
    public SupplierDTO save(@RequestBody SupplierDTO supplier) {
        return service.addNew(supplier);
    }

    @GetMapping("/get")
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
}

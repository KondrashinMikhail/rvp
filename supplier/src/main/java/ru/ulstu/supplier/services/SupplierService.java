package ru.ulstu.supplier.services;

import ru.ulstu.supplier.models.DTO.SupplierCutDTO;
import ru.ulstu.supplier.models.DTO.SupplierDTO;
import ru.ulstu.supplier.models.DTO.SupplierNumeratedDTO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface SupplierService {
    SupplierDTO addNew(SupplierDTO supplier);
    List<SupplierDTO> getAll();
    List<SupplierCutDTO> getOGRN();
    List<SupplierDTO> getByActive(Boolean isActive);
    SupplierDTO update(Long id, String name, Boolean isActive);
    SupplierDTO delete(Long id);

    CompletableFuture<List<SupplierNumeratedDTO>> getAllReportAsync(String correlationId);
    CompletableFuture<List<SupplierNumeratedDTO>> getByActiveReportAsync(Boolean isActive, String correlationId);
}
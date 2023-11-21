package ru.ulstu.reports.services;

import ru.ulstu.reports.models.DTO.SupplierNumeratedDTO;

import java.util.List;

public interface ReportsService {
    List<SupplierNumeratedDTO> getByActive(Boolean isActive, String correlationId);
    List<SupplierNumeratedDTO> getAll(String correlationId);
}
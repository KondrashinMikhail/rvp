package ru.ulstu.reports.services;

import ru.ulstu.reports.models.DTO.SupplierDTO;
import ru.ulstu.reports.models.DTO.SupplierNumeratedDTO;

import java.util.List;

public interface ReposrtsService {
    List<SupplierNumeratedDTO> getByActive(Boolean isActive);
    List<SupplierNumeratedDTO> getAll();
}
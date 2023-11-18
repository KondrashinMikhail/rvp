package ru.ulstu.reports.services;

import ru.ulstu.reports.models.DTO.SupplierDTO;

import java.util.List;

public interface ReposrtsService {
    List<SupplierDTO> getByActive(Boolean isActive);
}
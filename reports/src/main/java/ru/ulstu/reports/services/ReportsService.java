package ru.ulstu.reports.services;

import org.json.JSONArray;
import ru.ulstu.reports.models.DTO.SupplierNumeratedDTO;

import java.util.List;

public interface ReportsService {
    List<SupplierNumeratedDTO> getByActive(Boolean isActive);
    List<SupplierNumeratedDTO> getAll();

    JSONArray getJSONByActive(Boolean isActive);
    JSONArray getJSONAll();
}
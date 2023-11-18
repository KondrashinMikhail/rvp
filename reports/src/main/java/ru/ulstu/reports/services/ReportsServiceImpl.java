package ru.ulstu.reports.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ulstu.reports.models.DTO.SupplierDTO;
import ru.ulstu.reports.models.mappers.SupplierMapper;
import ru.ulstu.reports.repositories.SupplierRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReportsServiceImpl implements ReposrtsService {
    private final SupplierRepository repo;
    private final SupplierMapper mapper;

    @Override
    public List<SupplierDTO> getByActive(Boolean isActive) {
        return repo.findAllByIsActive(isActive).stream().map(mapper::mapToDTO).collect(Collectors.toList());
    }
}

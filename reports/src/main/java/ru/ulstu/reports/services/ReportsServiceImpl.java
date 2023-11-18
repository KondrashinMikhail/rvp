package ru.ulstu.reports.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ulstu.reports.models.DTO.SupplierNumeratedDTO;
import ru.ulstu.reports.models.mappers.SupplierMapper;
import ru.ulstu.reports.repositories.SupplierRepository;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@AllArgsConstructor
public class ReportsServiceImpl implements ReposrtsService {
    private final SupplierRepository repo;
    private final SupplierMapper mapper;

    @Override
    public List<SupplierNumeratedDTO> getByActive(Boolean isActive) {
        List<SupplierNumeratedDTO> list = repo.findAllByIsActive(isActive).stream().map(mapper::mapToNumeratedDTO).toList();
        AtomicInteger i = new AtomicInteger(1);
        list.forEach(elem -> elem.setNum(i.getAndIncrement()));
        return list;
    }

    @Override
    public List<SupplierNumeratedDTO> getAll() {
        List<SupplierNumeratedDTO> list =  repo.findAll().stream().map(mapper::mapToNumeratedDTO).toList();
        AtomicInteger i = new AtomicInteger(1);
        list.forEach(elem -> elem.setNum(i.getAndIncrement()));
        return list;
    }
}

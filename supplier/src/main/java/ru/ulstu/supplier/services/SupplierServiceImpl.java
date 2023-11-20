package ru.ulstu.supplier.services;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.ulstu.supplier.feign.ReportsClient;
import ru.ulstu.supplier.models.DTO.SupplierCutDTO;
import ru.ulstu.supplier.models.DTO.SupplierDTO;
import ru.ulstu.supplier.models.DTO.SupplierNumeratedDTO;
import ru.ulstu.supplier.models.Supplier;
import ru.ulstu.supplier.models.mappers.SupplierMapper;
import ru.ulstu.supplier.repositories.SupplierRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository repo;
    private final SupplierMapper mapper;
    private final ReportsClient reportsClient;

    @Override
    public SupplierDTO addNew(SupplierDTO supplier) {
        return mapper.mapToDTO(repo.save(mapper.mapFromDTO(supplier)));
    }

    @Override
    public List<SupplierDTO> getAll() {
        return repo.findAll().stream().map(mapper::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<SupplierCutDTO> getOGRN() {
        return repo.findAll().stream().map(mapper::mapToDTOCut).collect(Collectors.toList());
    }

    @Override
    public List<SupplierDTO> getByActive(Boolean isActive) {
        return repo.findAllByIsActive(isActive).stream().map(mapper::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public SupplierDTO update(Long id, String name, Boolean isActive) {
        Supplier supplier = repo.getOne(id);
        supplier.setName(name);
        supplier.setIsActive(isActive);
        return mapper.mapToDTO(repo.save(supplier));
    }

    @Override
    public SupplierDTO delete(Long id) {
        SupplierDTO s = mapper.mapToDTO(repo.getOne(id));
        repo.deleteById(id);
        return s;
    }

    @Override
    @Async
    public CompletableFuture<List<SupplierNumeratedDTO>> getAllReportAsync() {
        List<SupplierNumeratedDTO> data = reportsClient.getAllReport();
        return CompletableFuture.completedFuture(data);
    }

    @Override
    @Async
    public CompletableFuture<List<SupplierNumeratedDTO>> getByActiveReportAsync(Boolean isActive) {
        List<SupplierNumeratedDTO> data = isActive ? reportsClient.getActiveReport() : reportsClient.getDisabledReport();
        return CompletableFuture.completedFuture(data);
    }
}

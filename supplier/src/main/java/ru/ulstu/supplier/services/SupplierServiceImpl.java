package ru.ulstu.supplier.services;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

@Service
@AllArgsConstructor
@Log4j2
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository repo;
    private final SupplierMapper mapper;
    private final ReportsClient reportsClient;

    @Override
    public SupplierDTO addNew(SupplierDTO supplier) {
        SupplierDTO supplierDTO = mapper.mapToDTO(repo.save(mapper.mapFromDTO(supplier)));
        log.info(String.format("Успешно сохранен заказчик %s", supplierDTO.getId()));
        return supplierDTO;
    }

    @Override
    public List<SupplierDTO> getAll() {
        List<SupplierDTO> list = repo.findAll().stream().map(mapper::mapToDTO).toList();
        log.info("Получены все заказчики");
        return list;
    }

    @Override
    public List<SupplierCutDTO> getOGRN() {
        List<SupplierCutDTO> list = repo.findAll().stream().map(mapper::mapToDTOCut).toList();
        log.info("Получены ОГРН всех заказчиков");
        return list;
    }

    @Override
    public List<SupplierDTO> getByActive(Boolean isActive) {
        List<SupplierDTO> list = repo.findAllByIsActive(isActive).stream().map(mapper::mapToDTO).toList();
        log.info(isActive ? "Получены все активные заказчики" : "Получены все заблокированные заказчики");
        return list;
    }

    @Override
    public SupplierDTO update(Long id, String name, Boolean isActive) {
        Supplier supplier = repo.getOne(id);
        supplier.setName(name);
        supplier.setIsActive(isActive);
        SupplierDTO supplierDTO = mapper.mapToDTO(repo.save(supplier));
        log.info(String.format("Обновлена запись заказчика с id = %s", id));
        return supplierDTO;
    }

    @Override
    public SupplierDTO delete(Long id) {
        SupplierDTO s = mapper.mapToDTO(repo.getOne(id));
        repo.deleteById(id);
        log.info(String.format("Удалена запись заказчика с id = %s", id));
        return s;
    }

    @Override
    @Async
    public CompletableFuture<List<SupplierNumeratedDTO>> getAllReportAsync(String correlationId) {
        List<SupplierNumeratedDTO> data = reportsClient.getAllReport(correlationId);
        CompletableFuture<List<SupplierNumeratedDTO>> list = CompletableFuture.completedFuture(data);
        log.info("Асинхронно получены отчеты о всех заказчиках");
        return list;
    }

    @Override
    @Async
    public CompletableFuture<List<SupplierNumeratedDTO>> getByActiveReportAsync(Boolean isActive, String correlationId) {
        List<SupplierNumeratedDTO> data = isActive ? reportsClient.getActiveReport(correlationId) : reportsClient.getDisabledReport(correlationId);
        CompletableFuture<List<SupplierNumeratedDTO>> list = CompletableFuture.completedFuture(data);
        log.info(isActive ? "Асинхронно получены отчеты о всех активных заказчиках" : "Асинхронно получены отчеты о всех заблокированных заказчиках");
        return list;
    }
}

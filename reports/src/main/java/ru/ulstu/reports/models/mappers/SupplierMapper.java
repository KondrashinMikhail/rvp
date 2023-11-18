package ru.ulstu.reports.models.mappers;

import org.mapstruct.Mapper;
import ru.ulstu.reports.models.DTO.SupplierCutDTO;
import ru.ulstu.reports.models.DTO.SupplierDTO;
import ru.ulstu.reports.models.Supplier;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    SupplierDTO mapToDTO(Supplier supplier);
    Supplier mapFromDTO(SupplierDTO supplierDTO);
    SupplierCutDTO mapToDTOCut(Supplier supplier);
    Supplier mapFromDTOCut(SupplierCutDTO supplierCutDTO);
}

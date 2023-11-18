package ru.ulstu.supplier.models.mappers;

import org.mapstruct.Mapper;
import ru.ulstu.supplier.models.DTO.SupplierCutDTO;
import ru.ulstu.supplier.models.DTO.SupplierDTO;
import ru.ulstu.supplier.models.Supplier;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    SupplierDTO mapToDTO(Supplier supplier);
    Supplier mapFromDTO(SupplierDTO supplierDTO);
    SupplierCutDTO mapToDTOCut(Supplier supplier);
    Supplier mapFromDTOCut(SupplierCutDTO supplierCutDTO);
}

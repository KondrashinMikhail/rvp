package ru.ulstu.supplier.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SupplierNumeratedDTO {
    private Integer num;
    private Long id;
    private String name;
    private String ipName;
    private String oooName;
    private String ogrn;
    private String organizationName;
    private Boolean isActive;
}

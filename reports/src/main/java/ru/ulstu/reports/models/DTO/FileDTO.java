package ru.ulstu.reports.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class FileDTO {
    private byte[] bytes;
    private String filename;
}
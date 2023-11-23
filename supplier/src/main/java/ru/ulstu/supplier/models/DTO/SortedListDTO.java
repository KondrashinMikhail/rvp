package ru.ulstu.supplier.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SortedListDTO {
    private long time;
    private int threadsCount;
    private boolean isSorted;
    private List<Long> sourceList;
    private List<Long> sortedList;
}

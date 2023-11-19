package ru.ulstu.reports.models.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RabbitMessageDTO {
    private String message;
    private String routingKey;
}

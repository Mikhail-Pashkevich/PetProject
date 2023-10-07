package by.pashkevich.mikhail.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatisticDto {
    private String id;
    private Object battle;
    private LocalDateTime timestamp;
}

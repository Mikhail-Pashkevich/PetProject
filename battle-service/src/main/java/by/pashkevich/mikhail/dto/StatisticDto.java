package by.pashkevich.mikhail.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class StatisticDto {
    private Object battle;
    private final LocalDateTime timestamp = LocalDateTime.now();

    public StatisticDto(Object battle) {
        this.battle = battle;
    }
}

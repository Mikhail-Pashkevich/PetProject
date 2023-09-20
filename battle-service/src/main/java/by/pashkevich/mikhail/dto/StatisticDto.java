package by.pashkevich.mikhail.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class StatisticDto {
    private String message;
    private Object[] args;
    private Object result;
    private String exception;
    private final LocalDateTime timestamp = LocalDateTime.now();

    public StatisticDto(String message, Object[] args, Object result) {
        this(message, args, result, null);
    }

    public StatisticDto(String message, Object[] args, String exception) {
        this(message, args, null, exception);
    }

    public StatisticDto(String message, Object[] args, Object result, String exception) {
        this.message = message;
        this.args = args;
        this.result = result;
        this.exception = exception;
    }
}

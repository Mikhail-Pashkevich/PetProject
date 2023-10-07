package by.pashkevich.mikhail.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document("statistic")
public class Statistic {
    @Id
    private String id;
    private Object battle;
    private LocalDateTime timestamp;
}

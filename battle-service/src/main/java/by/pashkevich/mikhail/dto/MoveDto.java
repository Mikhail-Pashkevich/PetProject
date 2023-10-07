package by.pashkevich.mikhail.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoveDto {
    @Positive(message = "Battle id must be positive")
    private Long battleId;

    @PositiveOrZero(message = "Step must be  0 or more")
    private Integer i;

    @PositiveOrZero(message = "Step must be  0 or more")
    private Integer j;
}

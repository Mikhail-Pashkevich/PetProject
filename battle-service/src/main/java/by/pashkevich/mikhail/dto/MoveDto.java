package by.pashkevich.mikhail.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

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

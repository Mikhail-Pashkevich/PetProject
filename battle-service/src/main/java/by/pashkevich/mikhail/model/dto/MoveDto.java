package by.pashkevich.mikhail.model.dto;

import by.pashkevich.mikhail.model.entity.enums.Value;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoveDto {
    private Long battleId;
    private Integer i;
    private Integer j;
    private Value value;
}
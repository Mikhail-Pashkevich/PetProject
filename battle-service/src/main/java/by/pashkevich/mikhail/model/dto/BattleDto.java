package by.pashkevich.mikhail.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BattleDto {
    private Long id;
    private FieldDto fieldDto;
    private PlayerDto playerDtoX;
    private PlayerDto playerDtoO;
}

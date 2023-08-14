package by.pashkevich.mikhail.dto;

import by.pashkevich.mikhail.entity.enums.Value;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldDto {
    private Value[][] battleArea;
}

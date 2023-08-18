package by.pashkevich.mikhail.dto;

import by.pashkevich.mikhail.entity.enums.Value;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDto {
    @NotNull(message = "Value can't be null")
    private Value value;
}

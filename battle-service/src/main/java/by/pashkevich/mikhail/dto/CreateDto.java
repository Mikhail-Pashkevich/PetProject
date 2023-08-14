package by.pashkevich.mikhail.dto;

import by.pashkevich.mikhail.entity.enums.Value;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDto {
    @NotNull(message = "Value can't be null")
    private Value value;
}

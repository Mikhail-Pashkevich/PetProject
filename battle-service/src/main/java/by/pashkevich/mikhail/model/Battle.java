package by.pashkevich.mikhail.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Battle {
    private Long id;
    private Field field;
    private Status status;

    // maybe there sense to split Player and User
    private User playerX;
    private User playerO;

    // will create dto for all model
}

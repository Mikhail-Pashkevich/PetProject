package by.pashkevich.mikhail.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Field {
    // I have no idea how better save Field in database yet
    // There variant use String like X__O_X___
    private Value[][] array;
    private int size;
}

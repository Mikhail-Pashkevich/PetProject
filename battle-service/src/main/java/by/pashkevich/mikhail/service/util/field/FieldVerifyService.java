package by.pashkevich.mikhail.service.util.field;

import by.pashkevich.mikhail.model.entity.Field;
import by.pashkevich.mikhail.model.entity.enums.Value;

public interface FieldVerifyService {
    boolean isWin(Field field, Value value);

    boolean isCorrect(Field field);
}

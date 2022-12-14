package by.pashkevich.mikhail.service;

import by.pashkevich.mikhail.model.entity.Field;
import by.pashkevich.mikhail.model.entity.enums.Value;
import by.pashkevich.mikhail.model.util.Step;

public interface FieldVerifyService {
    boolean isWin(Field field, Value value);

    boolean isCorrectBattleArea(Value[][] battleArea);

    boolean isCorrectStep(Value[][] battleArea, Step step);
}

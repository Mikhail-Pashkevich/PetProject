package by.pashkevich.mikhail.service;

import by.pashkevich.mikhail.entity.Field;
import by.pashkevich.mikhail.entity.Step;
import by.pashkevich.mikhail.enums.Value;

public interface FieldVerifyService {
    boolean isWin(Field field, Value value);

    boolean isCorrectBattleArea(Value[][] battleArea);

    boolean isCorrectStep(Value[][] battleArea, Step step);
}

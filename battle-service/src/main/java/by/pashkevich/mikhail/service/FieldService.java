package by.pashkevich.mikhail.service;

import by.pashkevich.mikhail.entity.Field;
import by.pashkevich.mikhail.entity.Step;
import by.pashkevich.mikhail.entity.enums.BattleStatus;
import by.pashkevich.mikhail.entity.enums.Value;

public interface FieldService {
    BattleStatus move(Field field, Step step, Value value);

    Field create();
}

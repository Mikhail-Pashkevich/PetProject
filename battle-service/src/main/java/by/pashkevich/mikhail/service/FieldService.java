package by.pashkevich.mikhail.service;

import by.pashkevich.mikhail.model.entity.Field;
import by.pashkevich.mikhail.model.entity.enums.BattleStatus;
import by.pashkevich.mikhail.model.entity.enums.Value;

public interface FieldService {
    BattleStatus move(Field field, Integer step, Value value);

    Field save(Field field);
}

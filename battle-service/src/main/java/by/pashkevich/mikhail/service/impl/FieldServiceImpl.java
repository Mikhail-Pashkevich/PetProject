package by.pashkevich.mikhail.service.impl;

import by.pashkevich.mikhail.model.entity.Field;
import by.pashkevich.mikhail.model.entity.Step;
import by.pashkevich.mikhail.model.entity.enums.BattleStatus;
import by.pashkevich.mikhail.model.entity.enums.Value;
import by.pashkevich.mikhail.exception.IncorrectDataException;
import by.pashkevich.mikhail.repository.FieldRepository;
import by.pashkevich.mikhail.service.FieldService;
import by.pashkevich.mikhail.service.FieldVerifyService;
import by.pashkevich.mikhail.service.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {
    private final FieldRepository fieldRepository;
    private final SettingService settingService;

    private final FieldVerifyService fieldVerifyService;


    @Override
    public BattleStatus move(Field field, Step step, Value value) {
        Value[][] battleArea = field.getBattleArea();

        if (!fieldVerifyService.isCorrectBattleArea(battleArea) || !fieldVerifyService.isCorrectStep(battleArea, step)) {
            throw new IncorrectDataException("Incorrect battleArea or step");
        }

        field.setValueByStep(value, step);

        if (fieldVerifyService.isWin(field, value)) {
            return BattleStatus.FINISHED;
        }

        return switch (value) {
            case VALUE_X -> BattleStatus.WAIT_FOR_MOVE_O;
            case VALUE_O -> BattleStatus.WAIT_FOR_MOVE_X;
            default -> throw new IncorrectDataException("Can't process value: %s", value.name());
        };
    }

    @Override
    public Field create() {
        Integer rowSize = settingService.getRowSize();
        Field field = new Field(rowSize);

        return fieldRepository.save(field);
    }
}

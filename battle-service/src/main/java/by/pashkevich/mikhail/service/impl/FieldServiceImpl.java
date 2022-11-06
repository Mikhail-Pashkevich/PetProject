package by.pashkevich.mikhail.service.impl;

import by.pashkevich.mikhail.exception.IncorrectDataException;
import by.pashkevich.mikhail.model.entity.Field;
import by.pashkevich.mikhail.model.entity.enums.BattleStatus;
import by.pashkevich.mikhail.model.entity.enums.Value;
import by.pashkevich.mikhail.model.util.Step;
import by.pashkevich.mikhail.repository.FieldRepository;
import by.pashkevich.mikhail.repository.FieldSettingRepository;
import by.pashkevich.mikhail.service.FieldService;
import by.pashkevich.mikhail.service.FieldVerifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {
    private final FieldRepository fieldRepository;
    private final FieldSettingRepository fieldSettingRepository;

    private final FieldVerifyService fieldVerifyService;


    @Override
    public BattleStatus move(Field field, Step step, Value value) {
        Value[][] battleArea = field.getBattleArea();

        if (!fieldVerifyService.isCorrectBattleArea(battleArea) || !fieldVerifyService.isCorrectStep(battleArea, step)) {
            throw new IncorrectDataException("Incorrect battleArea or step");
        }

        field.setValueByStep(value, step);

        return fieldVerifyService.isWin(field, value) ? BattleStatus.FINISHED : BattleStatus.IN_PROGRESS;
    }

    @Override
    public Field create() {
        Integer rowSize = fieldSettingRepository.findAllRowSize().get(0);
        Field field = new Field(rowSize);

        return fieldRepository.save(field);
    }
}

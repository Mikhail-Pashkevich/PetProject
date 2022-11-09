package by.pashkevich.mikhail.service.impl;

import by.pashkevich.mikhail.model.entity.Field;
import by.pashkevich.mikhail.model.entity.enums.BattleStatus;
import by.pashkevich.mikhail.model.entity.enums.Value;
import by.pashkevich.mikhail.repository.FieldRepository;
import by.pashkevich.mikhail.service.FieldService;
import by.pashkevich.mikhail.service.FieldVerifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {
    private final FieldRepository fieldRepository;

    private final FieldVerifyService fieldVerifyService;


    @Override
    public BattleStatus move(Field field, Integer step, Value value) {
        if (!fieldVerifyService.isCorrect(field) || !isCorrectStep(field, step)) {
            return BattleStatus.INTERRUPTED;
        }

        field.getField()[step] = value;

        return fieldVerifyService.isWin(field, value) ? BattleStatus.FINISHED : BattleStatus.IN_PROGRESS;
    }

    @Override
    public Field save(Field field) {
        return fieldRepository.save(field);
    }

    private boolean isCorrectStep(Field field, Integer step) {
        return 0 <= step && step < field.getField().length && field.isEmpty(step);
    }
}

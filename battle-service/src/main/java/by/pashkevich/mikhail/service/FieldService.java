package by.pashkevich.mikhail.service;

import by.pashkevich.mikhail.model.entity.Field;
import by.pashkevich.mikhail.model.entity.enums.BattleStatus;
import by.pashkevich.mikhail.model.entity.enums.Value;
import by.pashkevich.mikhail.service.util.field.FieldVerifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FieldService {
    private final FieldVerifyService fieldVerifyService;


    public BattleStatus move(Field field, Integer step, Value value) {
        if (!fieldVerifyService.isCorrect(field) || !isCorrectStep(field, step)) {
            return BattleStatus.INTERRUPTED;
        }

        field.getField()[step] = value;

        return fieldVerifyService.isWin(field, value) ? BattleStatus.FINISHED : BattleStatus.IN_PROGRESS;
    }

    private boolean isCorrectStep(Field field, Integer step) {
        return 0 <= step && step < field.getField().length && field.isEmpty(step);
    }
}

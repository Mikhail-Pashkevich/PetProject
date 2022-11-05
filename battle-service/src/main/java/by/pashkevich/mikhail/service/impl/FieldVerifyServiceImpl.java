package by.pashkevich.mikhail.service.impl;

import by.pashkevich.mikhail.model.entity.Field;
import by.pashkevich.mikhail.model.entity.enums.Value;
import by.pashkevich.mikhail.model.util.Step;
import by.pashkevich.mikhail.repository.FieldSettingRepository;
import by.pashkevich.mikhail.service.FieldVerifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;


@Service
@RequiredArgsConstructor
public class FieldVerifyServiceImpl implements FieldVerifyService {
    private final FieldSettingRepository fieldSettingRepository;

    @Override
    public boolean isWin(Field field, Value value) {
        if (Value.VALUE_EMPTY.equals(value)) {
            return false;
        }

        boolean isWin = field.getColumns()
                .stream()
                .anyMatch(column -> Arrays.stream(column).allMatch(value::equals));

        isWin = isWin || field.getRows()
                .stream()
                .anyMatch(row -> Arrays.stream(row).allMatch(value::equals));

        isWin = isWin || Arrays.stream(field.getMainDiagonal()).allMatch(value::equals);

        isWin = isWin || Arrays.stream(field.getSideDiagonal()).allMatch(value::equals);

        return isWin;
    }

    @Override
    public boolean isCorrectBattleArea(Value[][] battleArea) {
        return isNotFull(battleArea) && isCorrectSize(battleArea) && isCorrectValues(battleArea);
    }

    @Override
    public boolean isCorrectStep(Value[][] battleArea, Step step) {
        return isLess(step.getI(), battleArea.length) && isLess(step.getJ(), battleArea.length)
                && isEmpty(battleArea, step);
    }

    private boolean isNotFull(Value[][] battleArea) {
        return Arrays.stream(battleArea)
                .flatMap(Arrays::stream)
                .toList()
                .contains(Value.VALUE_EMPTY);
    }

    private boolean isCorrectSize(Value[][] battleArea) {
        int rowSize = fieldSettingRepository.findAllRowSize().get(0);

        boolean isCorrect = battleArea.length == rowSize;
        for (Value[] row : battleArea) {
            isCorrect = isCorrect && row.length == rowSize;
        }

        return isCorrect;
    }

    private boolean isCorrectValues(Value[][] battleArea) {
        int counter = 0;

        for (Value[] row : battleArea) {
            for (Value cell : row) {
                if (cell.equals(Value.VALUE_X)) {
                    counter++;
                } else if (cell.equals(Value.VALUE_O)) {
                    counter--;
                }
            }
        }

        return counter == 0 || counter == 1;
    }

    private boolean isLess(Integer step, int end) {
        return 0 <= step && step < end;
    }

    private boolean isEmpty(Value[][] battleArea, Step step) {
        return Value.VALUE_EMPTY.equals(battleArea[step.getI()][step.getJ()]);
    }
}

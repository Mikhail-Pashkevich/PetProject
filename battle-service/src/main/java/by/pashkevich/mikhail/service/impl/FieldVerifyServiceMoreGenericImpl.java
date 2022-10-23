package by.pashkevich.mikhail.service.impl;

import by.pashkevich.mikhail.model.entity.Field;
import by.pashkevich.mikhail.model.entity.enums.Value;
import by.pashkevich.mikhail.service.FieldVerifyService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static by.pashkevich.mikhail.model.entity.enums.Value.VALUE_EMPTY;

@Service
public class FieldVerifyServiceMoreGenericImpl implements FieldVerifyService {
    @Override
    public boolean isWin(Field field, Value value) {
        if (VALUE_EMPTY.equals(value)) {
            return false;
        }

        BattleArea battleArea = new BattleArea(field.getField());

        boolean isWin = battleArea.getColumns()
                .stream()
                .anyMatch(column -> Arrays.stream(column).allMatch(value::equals));

        isWin = isWin || battleArea.getRows()
                .stream()
                .anyMatch(row -> Arrays.stream(row).allMatch(value::equals));

        isWin = isWin || Arrays.stream(battleArea.getMainDiagonal()).allMatch(value::equals);

        isWin = isWin || Arrays.stream(battleArea.getSideDiagonal()).allMatch(value::equals);

        return isWin;
    }

    @Override
    public boolean isCorrect(Field field) {
        return field.isNotFull() && field.isCorrectSize() && isCorrectValues(field.getField());
    }

    private boolean isCorrectValues(Value[] field) {
        int counter = 0;

        for (Value value : field) {
            if (value.equals(Value.VALUE_X)) {
                counter++;
            } else if (value.equals(Value.VALUE_O)) {
                counter--;
            }
        }

        return counter == 0 || counter == 1;
    }

    //TODO: This class maybe will replace current class Field
    private static class BattleArea {
        private final Value[][] battleArea;

        public BattleArea(Value[] field) {
            int battleAreaColumnSize = (int) Math.sqrt(field.length);

            battleArea = new Value[battleAreaColumnSize][battleAreaColumnSize];

            for (int i = 0, k = 0; i < battleAreaColumnSize; i++) {
                for (int j = 0; j < battleAreaColumnSize; j++) {
                    battleArea[i][j] = field[k];
                    k++;
                }
            }
        }

        public List<Value[]> getRows() {
            return Arrays.stream(battleArea).toList();
        }

        public List<Value[]> getColumns() {
            List<Value[]> columns = new ArrayList<>();

            for (int i = 0; i < battleArea.length; i++) {
                Value[] column = new Value[battleArea.length];

                for (int j = 0; j < battleArea.length; j++) {
                    column[j] = battleArea[j][i];
                }

                columns.add(column);
            }

            return columns;
        }

        public Value[] getMainDiagonal() {
            Value[] diagonal = new Value[battleArea.length];

            for (int i = 0; i < battleArea.length; i++) {
                diagonal[i] = battleArea[i][i];
            }

            return diagonal;
        }

        public Value[] getSideDiagonal() {
            Value[] diagonal = new Value[battleArea.length];

            for (int j = battleArea.length - 1, i = 0; j >= 0; j--, i++) {
                diagonal[i] = battleArea[i][j];
            }

            return diagonal;
        }
    }
}

package by.pashkevich.mikhail.entity;

import by.pashkevich.mikhail.entity.enums.Value;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Convert(converter = BattleAreaConverter.class)
    private Value[][] battleArea;


    public Field(int rowSize) {
        battleArea = new Value[rowSize][rowSize];

        for (Value[] row : battleArea) {
            Arrays.fill(row, Value.VALUE_EMPTY);
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

    public void setValueByStep(Value value, Step step) {
        battleArea[step.getI()][step.getJ()] = value;
    }


    private static class BattleAreaConverter implements AttributeConverter<Value[][], String> {
        private static final String CELL_SEPARATOR = ",";
        private static final String ROW_SEPARATOR = ";";


        @Override
        public String convertToDatabaseColumn(Value[][] battleArea) {
            if (battleArea == null) {
                return "";
            }
            return Arrays.stream(battleArea)
                    .map(row -> Arrays.stream(row)
                            .map(Value::name)
                            .collect(Collectors.joining(CELL_SEPARATOR))
                    )
                    .collect(Collectors.joining(ROW_SEPARATOR));
        }

        @Override
        public Value[][] convertToEntityAttribute(String dbData) {
            if (dbData.isEmpty()) {
                return null;
            }
            return Arrays.stream(dbData.split(ROW_SEPARATOR))
                    .map(row -> Arrays.stream(row.split(CELL_SEPARATOR))
                            .map(Value::valueOf)
                            .toArray(Value[]::new)
                    )
                    .toArray(Value[][]::new);
        }
    }
}

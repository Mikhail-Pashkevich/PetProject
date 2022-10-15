package by.pashkevich.mikhail.model.entity;

import by.pashkevich.mikhail.model.entity.enums.Value;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Arrays;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Entity
@Table
public class Field {
    private static final int FIELD_SIZE = 9;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Convert(converter = FieldConverter.class)
    private Value[] field;

    public Field() {
        field = new Value[FIELD_SIZE];
        Arrays.fill(field, Value.VALUE_EMPTY);
    }

    public boolean isNotFull() {
        return Arrays.asList(field).contains(Value.VALUE_EMPTY);
    }

    public boolean isCorrectSize() {
        return field.length == FIELD_SIZE;
    }

    public boolean isEmpty(int index) {
        return field[index].equals(Value.VALUE_EMPTY);
    }

    private static class FieldConverter implements AttributeConverter<Value[], String> {
        @Override
        public String convertToDatabaseColumn(Value[] field) {
            return Arrays.stream(field)
                    .map(Enum::toString)
                    .collect(Collectors.joining(", "));
        }

        @Override
        public Value[] convertToEntityAttribute(String dbData) {
            return Arrays.stream(dbData.split(", "))
                    .map(Value::valueOf)
                    .toArray(Value[]::new);
        }
    }
}

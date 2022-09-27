package by.pashkevich.mikhail.model.entity;

import by.pashkevich.mikhail.model.entity.enums.Value;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

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
    @Convert(converter = FieldConverter.class)
    private Value[][] field;


    private static class FieldConverter implements AttributeConverter<Value[][], String> {

        @Override
        public String convertToDatabaseColumn(Value[][] field) {
            return Arrays.stream(field)
                    .flatMap(Arrays::stream)
                    .map(Enum::toString)
                    .collect(joining(", "));
        }

        @Override
        public Value[][] convertToEntityAttribute(String dbData) {
            List<Value> collect = Arrays.stream(dbData.split(", "))
                    .map(Value::valueOf)
                    .collect(Collectors.toList());

            Value[][] values = new Value[3][3];

            for (int i = 0, k = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++, k++) {
                    values[i][j] = collect.get(k);
                }
            }

            return values;
        }
    }
}

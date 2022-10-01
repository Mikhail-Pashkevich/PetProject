package by.pashkevich.mikhail.model.entity;

import by.pashkevich.mikhail.model.entity.enums.Value;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;

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
    private Value[] field;


    private static class FieldConverter implements AttributeConverter<Value[], String> {
        @Override
        public String convertToDatabaseColumn(Value[] field) {
            return Arrays.stream(field)
                    .map(Enum::toString)
                    .collect(joining(", "));
        }

        @Override
        public Value[] convertToEntityAttribute(String dbData) {
            return Arrays.stream(dbData.split(", "))
                    .map(Value::valueOf)
                    .toArray(Value[]::new);
        }
    }
}

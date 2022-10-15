package by.pashkevich.mikhail.service.util.field;

import by.pashkevich.mikhail.model.entity.Field;
import by.pashkevich.mikhail.model.entity.enums.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static by.pashkevich.mikhail.service.util.field.CheckValue.ANY;
import static by.pashkevich.mikhail.service.util.field.CheckValue.VALUE;

@Service
public class FieldVerifyService {
    private final List<CheckValue[]> winCombinationList = Arrays.asList(
            new CheckValue[]{VALUE, ANY, ANY, VALUE, ANY, ANY, VALUE, ANY, ANY},    //first column
            new CheckValue[]{ANY, VALUE, ANY, ANY, VALUE, ANY, ANY, VALUE, ANY},    //second column
            new CheckValue[]{ANY, ANY, VALUE, ANY, ANY, VALUE, ANY, ANY, VALUE},    //third column
            new CheckValue[]{VALUE, VALUE, VALUE, ANY, ANY, ANY, ANY, ANY, ANY},    //first row
            new CheckValue[]{ANY, ANY, ANY, VALUE, VALUE, VALUE, ANY, ANY, ANY},    //second row
            new CheckValue[]{ANY, ANY, ANY, ANY, ANY, ANY, VALUE, VALUE, VALUE},    //third row
            new CheckValue[]{VALUE, ANY, ANY, ANY, VALUE, ANY, ANY, ANY, VALUE},    //main diagonal
            new CheckValue[]{ANY, ANY, VALUE, ANY, VALUE, ANY, VALUE, ANY, ANY}     //secondary diagonal
    );


    public boolean isWin(Field field, Value value) {
        return winCombinationList.stream().anyMatch(winCombination -> isWinField(winCombination, field.getField(), value));
    }

    public boolean isCorrect(Field field) {
        return field.isNotFull() && field.isCorrectSizeAndValues();
    }

    private boolean isWinField(CheckValue[] checkValues, Value[] values, Value value) {
        return IntStream.range(0, checkValues.length).allMatch(i -> isEqualsValues(checkValues[i], values[i], value));
    }

    private boolean isEqualsValues(CheckValue checkValue, Value value1, Value value2) {
        return switch (checkValue) {
            case VALUE -> value1.equals(value2);
            case ANY -> true;
        };
    }
}

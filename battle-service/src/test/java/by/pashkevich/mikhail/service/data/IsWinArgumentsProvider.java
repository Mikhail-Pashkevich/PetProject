package by.pashkevich.mikhail.service.data;

import by.pashkevich.mikhail.model.entity.Field;
import by.pashkevich.mikhail.model.entity.enums.Value;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

import static by.pashkevich.mikhail.model.entity.enums.Value.*;
import static by.pashkevich.mikhail.service.CommonMethods.anyId;


public class IsWinArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        Value[] arrayWithValueX = {
                VALUE_X, VALUE_EMPTY, VALUE_EMPTY, VALUE_X, VALUE_EMPTY, VALUE_EMPTY, VALUE_X, VALUE_EMPTY, VALUE_EMPTY
        };
        Value[] arrayWithValueO = {
                VALUE_O, VALUE_O, VALUE_O, VALUE_EMPTY, VALUE_EMPTY, VALUE_EMPTY, VALUE_EMPTY, VALUE_EMPTY, VALUE_EMPTY
        };
        Value[] emptyArray = {
                VALUE_EMPTY, VALUE_EMPTY, VALUE_EMPTY, VALUE_EMPTY, VALUE_EMPTY, VALUE_EMPTY, VALUE_EMPTY, VALUE_EMPTY, VALUE_EMPTY
        };
        Value[] arrayWithDifValue = {
                VALUE_O, VALUE_EMPTY, VALUE_EMPTY, VALUE_EMPTY, VALUE_EMPTY, VALUE_EMPTY, VALUE_EMPTY, VALUE_EMPTY, VALUE_X
        };


        return Stream.of(
                Arguments.of(new Field(anyId(), arrayWithValueX), VALUE_X, true),
                Arguments.of(new Field(anyId(), arrayWithValueX), VALUE_O, false),
                Arguments.of(new Field(anyId(), arrayWithValueX), VALUE_EMPTY, false),

                Arguments.of(new Field(anyId(), arrayWithValueO), VALUE_X, false),
                Arguments.of(new Field(anyId(), arrayWithValueO), VALUE_O, true),
                Arguments.of(new Field(anyId(), arrayWithValueO), VALUE_EMPTY, false),

                Arguments.of(new Field(anyId(), emptyArray), VALUE_X, false),
                Arguments.of(new Field(anyId(), emptyArray), VALUE_O, false),
                Arguments.of(new Field(anyId(), emptyArray), VALUE_EMPTY, false),

                Arguments.of(new Field(anyId(), arrayWithDifValue), VALUE_X, false),
                Arguments.of(new Field(anyId(), arrayWithDifValue), VALUE_O, false),
                Arguments.of(new Field(anyId(), arrayWithDifValue), VALUE_EMPTY, false)
        );
    }
}

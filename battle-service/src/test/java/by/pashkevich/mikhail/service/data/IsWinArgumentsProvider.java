package by.pashkevich.mikhail.service.data;

import by.pashkevich.mikhail.entity.Field;
import by.pashkevich.mikhail.entity.enums.Value;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

import static by.pashkevich.mikhail.entity.enums.Value.*;
import static by.pashkevich.mikhail.service.CommonMethods.anyId;


public class IsWinArgumentsProvider implements ArgumentsProvider {
    private final Value[][] arrayWithValueX = {
            {VALUE_X, VALUE_EMPTY, VALUE_EMPTY},
            {VALUE_X, VALUE_EMPTY, VALUE_EMPTY},
            {VALUE_X, VALUE_EMPTY, VALUE_EMPTY}
    };
    private final Value[][] arrayWithValueO = {
            {VALUE_O, VALUE_O, VALUE_O},
            {VALUE_EMPTY, VALUE_EMPTY, VALUE_EMPTY},
            {VALUE_EMPTY, VALUE_EMPTY, VALUE_EMPTY}
    };
    private final Value[][] emptyArray = {
            {VALUE_EMPTY, VALUE_EMPTY, VALUE_EMPTY},
            {VALUE_EMPTY, VALUE_EMPTY, VALUE_EMPTY},
            {VALUE_EMPTY, VALUE_EMPTY, VALUE_EMPTY}
    };
    private final Value[][] arrayWithDifValue = {
            {VALUE_O, VALUE_EMPTY, VALUE_EMPTY},
            {VALUE_EMPTY, VALUE_EMPTY, VALUE_EMPTY},
            {VALUE_EMPTY, VALUE_EMPTY, VALUE_X}
    };


    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
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

package by.pashkevich.mikhail.service.util.field.data;

import by.pashkevich.mikhail.model.entity.Field;
import by.pashkevich.mikhail.model.entity.enums.Value;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

import static by.pashkevich.mikhail.model.entity.enums.Value.*;
import static by.pashkevich.mikhail.service.CommonMethods.anyId;

public class IsCorrectArgumentsProvider implements ArgumentsProvider {
    private static final int ANY_INCORRECT_FIELD_SIZE = 1;


    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        Value[] fullArray = {
                VALUE_X, VALUE_O, VALUE_O, VALUE_O, VALUE_X, VALUE_X, VALUE_X, VALUE_X, VALUE_O
        };
        Value[] arrayWithIncorrectSize = {
                VALUE_X, VALUE_EMPTY
        };
        Value[] arrayWithMoreX = {
                VALUE_X, VALUE_X, VALUE_X, VALUE_O, VALUE_EMPTY, VALUE_EMPTY, VALUE_EMPTY, VALUE_EMPTY, VALUE_EMPTY
        };
        Value[] arrayWithMoreO = {
                VALUE_O, VALUE_O, VALUE_X, VALUE_EMPTY, VALUE_EMPTY, VALUE_EMPTY, VALUE_EMPTY, VALUE_EMPTY, VALUE_EMPTY
        };
        Value[] correctArray = {
                VALUE_X, VALUE_O, VALUE_O, VALUE_EMPTY, VALUE_X, VALUE_EMPTY, VALUE_EMPTY, VALUE_EMPTY, VALUE_EMPTY
        };


        return Stream.of(
                Arguments.of(new Field(anyId(), fullArray), false),
                Arguments.of(new Field(anyId(), arrayWithIncorrectSize), false),
                Arguments.of(new Field(anyId(), arrayWithMoreX), false),
                Arguments.of(new Field(anyId(), arrayWithMoreO), false),
                Arguments.of(new Field(anyId(), correctArray), true)
        );
    }
}

package by.pashkevich.mikhail.service.data;

import by.pashkevich.mikhail.model.entity.Field;
import by.pashkevich.mikhail.model.entity.enums.Value;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

import static by.pashkevich.mikhail.model.entity.enums.BattleStatus.*;
import static by.pashkevich.mikhail.model.entity.enums.Value.*;
import static by.pashkevich.mikhail.service.CommonMethods.*;

public class MoveArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        Value[] incorrectArray = {
                VALUE_X, VALUE_EMPTY
        };
        Value[] correctArray = {
                VALUE_X, VALUE_O, VALUE_O, VALUE_EMPTY, VALUE_X, VALUE_EMPTY, VALUE_EMPTY, VALUE_EMPTY, VALUE_EMPTY
        };


        return Stream.of(
                Arguments.of(new Field(anyId(), incorrectArray), anyStep(), anyValue(), INTERRUPTED),
                Arguments.of(new Field(anyId(), correctArray), -1, anyValue(), INTERRUPTED),
                Arguments.of(new Field(anyId(), correctArray), 9, anyValue(), INTERRUPTED),
                Arguments.of(new Field(anyId(), correctArray), 0, anyValue(), INTERRUPTED),
                Arguments.of(new Field(anyId(), correctArray), 3, VALUE_X, IN_PROGRESS),
                Arguments.of(new Field(anyId(), correctArray), 8, VALUE_X, FINISHED)
        );
    }
}

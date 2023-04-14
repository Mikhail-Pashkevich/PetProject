package by.pashkevich.mikhail.service.data;

import by.pashkevich.mikhail.enums.Value;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

import static by.pashkevich.mikhail.enums.Value.*;

public class IsCorrectArgumentsProvider implements ArgumentsProvider {
    private final Value[][] fullArray = {
            {VALUE_X, VALUE_O, VALUE_O},
            {VALUE_O, VALUE_X, VALUE_X},
            {VALUE_X, VALUE_X, VALUE_O}
    };
    private final Value[][] arrayWithIncorrectSize = {
            {VALUE_X, VALUE_EMPTY}
    };
    private final Value[][] arrayWithMoreX = {
            {VALUE_X, VALUE_X, VALUE_X},
            {VALUE_O, VALUE_EMPTY, VALUE_EMPTY},
            {VALUE_EMPTY, VALUE_EMPTY, VALUE_EMPTY}
    };
    private final Value[][] arrayWithMoreO = {
            {VALUE_O, VALUE_O, VALUE_X},
            {VALUE_EMPTY, VALUE_EMPTY, VALUE_EMPTY},
            {VALUE_EMPTY, VALUE_EMPTY, VALUE_EMPTY}
    };
    private final Value[][] correctArray = {
            {VALUE_X, VALUE_O, VALUE_O},
            {VALUE_EMPTY, VALUE_X, VALUE_EMPTY},
            {VALUE_EMPTY, VALUE_EMPTY, VALUE_EMPTY}
    };

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(fullArray, false),
                Arguments.of(arrayWithIncorrectSize, false),
                Arguments.of(arrayWithMoreX, false),
                Arguments.of(arrayWithMoreO, false),
                Arguments.of(correctArray, true)
        );
    }
}

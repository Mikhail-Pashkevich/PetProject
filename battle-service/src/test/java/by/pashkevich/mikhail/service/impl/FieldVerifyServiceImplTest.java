package by.pashkevich.mikhail.service.impl;

import by.pashkevich.mikhail.model.entity.Field;
import by.pashkevich.mikhail.model.entity.enums.Value;
import by.pashkevich.mikhail.model.util.Step;
import by.pashkevich.mikhail.service.CommonMethods;
import by.pashkevich.mikhail.service.SettingService;
import by.pashkevich.mikhail.service.data.IsCorrectArgumentsProvider;
import by.pashkevich.mikhail.service.data.IsWinArgumentsProvider;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FieldVerifyServiceImplTest {
    @Mock(lenient = true)
    private SettingService settingService;

    @InjectMocks
    private FieldVerifyServiceImpl fieldVerifyService;


    private static Stream<Arguments> getStepsAndExpectedResult() {
        return Stream.of(
                Arguments.of(new Step(0, 0), true),
                Arguments.of(new Step(-1, 0), false),
                Arguments.of(new Step(Integer.MAX_VALUE, 0), false),
                Arguments.of(new Step(0, -1), false),
                Arguments.of(new Step(0, Integer.MAX_VALUE), false)
        );
    }

    @ParameterizedTest
    @ArgumentsSource(IsWinArgumentsProvider.class)
    void isWin(Field field, Value value, boolean expectedResult) {
        assertEquals(expectedResult, fieldVerifyService.isWin(field, value));
    }

    @ParameterizedTest
    @ArgumentsSource(IsCorrectArgumentsProvider.class)
    void isCorrectBattleArea(Value[][] battleArea, boolean expectedResult) {
        Mockito.when(settingService.getRowSize()).thenReturn(3);

        assertEquals(expectedResult, fieldVerifyService.isCorrectBattleArea(battleArea));
    }

    @ParameterizedTest
    @MethodSource("getStepsAndExpectedResult")
    void isCorrectStep_checkStepPosition(Step step, boolean expectedResult) {
        Value[][] battleArea = CommonMethods.anyBattleArea();

        assertEquals(expectedResult, fieldVerifyService.isCorrectStep(battleArea, step));
    }

    @ParameterizedTest
    @EnumSource(value = Value.class, names = {"VALUE_X", "VALUE_O", "VALUE_EMPTY"})
    void isCorrectStep_checkIsEmptyCell(Value value) {
        Value[][] battleArea = CommonMethods.anyBattleArea();
        Step step = CommonMethods.anyStep();
        boolean expectedResult = Value.VALUE_EMPTY.equals(value);

        battleArea[step.getJ()][step.getJ()] = value;

        assertEquals(expectedResult, fieldVerifyService.isCorrectStep(battleArea, step));
    }
}

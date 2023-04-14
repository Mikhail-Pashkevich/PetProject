package by.pashkevich.mikhail.service.impl;

import by.pashkevich.mikhail.model.entity.Field;
import by.pashkevich.mikhail.model.entity.Step;
import by.pashkevich.mikhail.model.entity.enums.BattleStatus;
import by.pashkevich.mikhail.model.entity.enums.Value;
import by.pashkevich.mikhail.exception.IncorrectDataException;
import by.pashkevich.mikhail.repository.FieldRepository;
import by.pashkevich.mikhail.service.FieldVerifyService;
import by.pashkevich.mikhail.service.SettingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static by.pashkevich.mikhail.service.CommonMethods.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class FieldServiceImplTest {
    @Mock
    private FieldRepository fieldRepository;

    @Mock
    private SettingService settingService;

    @Mock(lenient = true)
    private FieldVerifyService fieldVerifyService;

    @InjectMocks
    private FieldServiceImpl fieldService;


    private static Stream<Arguments> getFieldVerifyServiceArgumentsAndExpectedBattleStatus() {
        return Stream.of(
                Arguments.of(true, Value.VALUE_X, BattleStatus.FINISHED),
                Arguments.of(true, Value.VALUE_O, BattleStatus.FINISHED),
                Arguments.of(false, Value.VALUE_X, BattleStatus.WAIT_FOR_MOVE_O),
                Arguments.of(false, Value.VALUE_O, BattleStatus.WAIT_FOR_MOVE_X)
        );
    }

    private static Stream<Arguments> getFieldVerifyServiceArguments() {
        return Stream.of(
                Arguments.of(false, true),
                Arguments.of(true, false),
                Arguments.of(false, false)
        );
    }

    @ParameterizedTest
    @MethodSource("getFieldVerifyServiceArgumentsAndExpectedBattleStatus")
    public void move_assertReturnBattleStatus(boolean isWin, Value value, BattleStatus expectedStatus) {
        Field field = anyFieldWithBattleArea();
        Step step = anyStep();

        Mockito.when(fieldVerifyService.isCorrectBattleArea(Mockito.any())).thenReturn(true);
        Mockito.when(fieldVerifyService.isCorrectStep(Mockito.any(), Mockito.any())).thenReturn(true);
        Mockito.when(fieldVerifyService.isWin(Mockito.any(), Mockito.any())).thenReturn(isWin);

        BattleStatus battleStatus = fieldService.move(field, step, value);

        assertEquals(value, field.getBattleArea()[step.getI()][step.getJ()]);
        assertEquals(expectedStatus, battleStatus);
    }

    @ParameterizedTest
    @MethodSource("getFieldVerifyServiceArguments")
    public void move_assertThrowException(boolean isCorrectBattleArea, boolean isCorrectStep) {
        Mockito.when(fieldVerifyService.isCorrectBattleArea(Mockito.any())).thenReturn(isCorrectBattleArea);
        Mockito.when(fieldVerifyService.isCorrectStep(Mockito.any(), Mockito.any())).thenReturn(isCorrectStep);

        assertThrows(IncorrectDataException.class, () -> fieldService.move(anyFieldWithBattleArea(), anyStep(), anyValue()));
    }

    @Test
    public void create() {
        int rowSize = 3;
        Field field = new Field(rowSize);

        Mockito.when(settingService.getRowSize()).thenReturn(rowSize);
        Mockito.when(fieldRepository.save(field)).thenAnswer(invocation -> invocation.getArgument(0));

        assertEquals(field, fieldService.create());
    }
}

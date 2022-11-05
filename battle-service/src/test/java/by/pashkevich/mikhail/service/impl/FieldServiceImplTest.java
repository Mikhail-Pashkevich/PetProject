package by.pashkevich.mikhail.service.impl;

import by.pashkevich.mikhail.model.entity.Field;
import by.pashkevich.mikhail.model.entity.enums.BattleStatus;
import by.pashkevich.mikhail.model.entity.enums.Value;
import by.pashkevich.mikhail.model.util.Step;
import by.pashkevich.mikhail.repository.FieldRepository;
import by.pashkevich.mikhail.service.CommonMethods;
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

@ExtendWith(MockitoExtension.class)
public class FieldServiceImplTest {
    @Mock
    private FieldRepository fieldRepository;

    @Mock(lenient = true)
    private FieldVerifyServiceImpl fieldVerifyService;

    @InjectMocks
    private FieldServiceImpl fieldService;


    private static Stream<Arguments> getFieldVerifyServiceArgumentsAndExpectedBattleStatus() {
        return Stream.of(
                Arguments.of(true, true, true, BattleStatus.FINISHED),
                Arguments.of(false, true, CommonMethods.anyBoolean(), BattleStatus.INTERRUPTED),
                Arguments.of(true, false, CommonMethods.anyBoolean(), BattleStatus.INTERRUPTED),
                Arguments.of(false, false, CommonMethods.anyBoolean(), BattleStatus.INTERRUPTED),

                Arguments.of(true, true, false, BattleStatus.IN_PROGRESS),
                Arguments.of(false, true, CommonMethods.anyBoolean(), BattleStatus.INTERRUPTED),
                Arguments.of(true, false, CommonMethods.anyBoolean(), BattleStatus.INTERRUPTED),
                Arguments.of(false, false, CommonMethods.anyBoolean(), BattleStatus.INTERRUPTED)
        );
    }

    @ParameterizedTest
    @MethodSource("getFieldVerifyServiceArgumentsAndExpectedBattleStatus")
    public void move(boolean isCorrectBattleArea, boolean isCorrectStep, boolean isWin, BattleStatus expectedStatus) {
        Value[][] battleArea = CommonMethods.anyBattleArea();
        Field field = new Field(anyId(), battleArea);
        Step step = anyStep();
        Value value = anyValue();
        Value expectedValue = BattleStatus.INTERRUPTED.equals(expectedStatus) ? Value.VALUE_EMPTY : value;

        Mockito.when(fieldVerifyService.isCorrectBattleArea(Mockito.any())).thenReturn(isCorrectBattleArea);
        Mockito.when(fieldVerifyService.isCorrectStep(Mockito.any(), Mockito.any())).thenReturn(isCorrectStep);
        Mockito.when(fieldVerifyService.isWin(Mockito.any(), Mockito.any())).thenReturn(isWin);

        BattleStatus battleStatus = fieldService.move(field, step, value);

        assertEquals(expectedValue, field.getBattleArea()[step.getI()][step.getJ()]);
        assertEquals(expectedStatus, battleStatus);
    }

    @Test
    public void save() {
        Field field = new Field();

        Mockito.when(fieldRepository.save(field)).thenReturn(field);

        assertEquals(field, fieldService.save(field));
    }
}

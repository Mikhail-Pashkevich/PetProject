package by.pashkevich.mikhail.service.util.field;

import by.pashkevich.mikhail.model.entity.Field;
import by.pashkevich.mikhail.model.entity.enums.Value;
import by.pashkevich.mikhail.service.util.field.data.IsCorrectArgumentsProvider;
import by.pashkevich.mikhail.service.util.field.data.IsWinArgumentsProvider;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FieldVerifyServiceImplTest {
    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private FieldVerifyServiceImpl fieldVerifyService;


    @ParameterizedTest
    @ArgumentsSource(IsWinArgumentsProvider.class)
    void isWin(Field field, Value value, boolean expectedResult) {
        assertEquals(expectedResult, fieldVerifyService.isWin(field, value));
    }

    @ParameterizedTest
    @ArgumentsSource(IsCorrectArgumentsProvider.class)
    void isCorrect(Field field, boolean expectedResult) {
        assertEquals(expectedResult, fieldVerifyService.isCorrect(field));
    }
}

package by.pashkevich.mikhail.service.impl;

import by.pashkevich.mikhail.model.entity.Field;
import by.pashkevich.mikhail.model.entity.enums.BattleStatus;
import by.pashkevich.mikhail.model.entity.enums.Value;
import by.pashkevich.mikhail.repository.FieldRepository;
import by.pashkevich.mikhail.service.data.MoveArgumentsProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FieldServiceImplTest {
    @Mock
    private FieldRepository fieldRepository;

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private FieldVerifyServiceImpl fieldVerifyService;

    @InjectMocks
    private FieldServiceImpl fieldService;


    @ParameterizedTest
    @ArgumentsSource(MoveArgumentsProvider.class)
    public void move(Field field, Integer step, Value value, BattleStatus expectedResult) {
        BattleStatus battleStatus = fieldService.move(field, step, value);

        assertEquals(expectedResult, battleStatus);
    }

    @Test
    public void save() {
        Field field = new Field();

        Mockito.when(fieldRepository.save(field)).thenReturn(field);

        assertEquals(field, fieldService.save(field));
    }
}

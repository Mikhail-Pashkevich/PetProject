package by.pashkevich.mikhail.service.impl;

import by.pashkevich.mikhail.exception.AccessException;
import by.pashkevich.mikhail.exception.BattleUnavailableException;
import by.pashkevich.mikhail.exception.IncorrectDataException;
import by.pashkevich.mikhail.model.User;
import by.pashkevich.mikhail.model.entity.Battle;
import by.pashkevich.mikhail.model.entity.enums.BattleStatus;
import by.pashkevich.mikhail.model.entity.enums.Value;
import by.pashkevich.mikhail.repository.BattleRepository;
import by.pashkevich.mikhail.service.FieldService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static by.pashkevich.mikhail.service.CommonMethods.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BattleServiceImplTest {
    @Mock
    private BattleRepository battleRepository;

    @Mock
    private FieldService fieldService;

    @InjectMocks
    private BattleServiceImpl battleService;


    private static Stream<Arguments> getArgumentsForProcessing() {
        return Stream.of(
                Arguments.of(anyUser(1L), BattleStatus.WAIT_FOR_MOVE_X, BattleStatus.WAIT_FOR_MOVE_O),
                Arguments.of(anyUser(2L), BattleStatus.WAIT_FOR_MOVE_O, BattleStatus.WAIT_FOR_MOVE_X),
                Arguments.of(anyUser(1L), BattleStatus.WAIT_FOR_MOVE_X, BattleStatus.FINISHED),
                Arguments.of(anyUser(2L), BattleStatus.WAIT_FOR_MOVE_O, BattleStatus.FINISHED)
        );
    }

    public static Stream<Arguments> getArgumentsForThrowException() {
        return Stream.of(
                Arguments.of(BattleStatus.FINISHED, BattleUnavailableException.class, anyUser()),
                Arguments.of(BattleStatus.INTERRUPTED, BattleUnavailableException.class, anyUser()),
                Arguments.of(BattleStatus.WAIT_FOR_PLAYER, BattleUnavailableException.class, anyUser()),
                Arguments.of(BattleStatus.WAIT_FOR_MOVE_O, AccessException.class, anyUser(3L)),
                Arguments.of(BattleStatus.WAIT_FOR_MOVE_X, AccessException.class, anyUser(3L)),
                Arguments.of(BattleStatus.WAIT_FOR_MOVE_O, IncorrectDataException.class, anyUser(1L)),
                Arguments.of(BattleStatus.WAIT_FOR_MOVE_X, IncorrectDataException.class, anyUser(2L))
        );
    }

    @Test
    void create() {
        User anyUser = anyUser();

        Mockito.when(fieldService.create()).thenReturn(anyField());
        Mockito.when(battleRepository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));

        Battle actualResult = battleService.create(Value.VALUE_X, anyUser);

        assertEquals(anyUser, actualResult.getPlayerX());
        assertNull(actualResult.getPlayerO());
        assertNotNull(actualResult.getField());
        assertEquals(BattleStatus.WAIT_FOR_PLAYER, actualResult.getBattleStatus());
    }

    @Test
    void join_whenUserJoinAsPlayerX() {
        User playerX = anyUser(1L);
        Battle battleWithPlayerO = new Battle();
        User playerO = anyUser(2L);
        battleWithPlayerO.setPlayerO(playerO);
        List<Battle> battleList = List.of(battleWithPlayerO);

        Mockito.when(battleRepository.findAllByBattleStatus(Mockito.any())).thenReturn(battleList);
        Mockito.when(battleRepository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));

        Battle actualResult = battleService.join(playerX);

        assertNotNull(actualResult.getPlayerO());
        assertNotEquals(playerX, actualResult.getPlayerO());
        assertEquals(playerX, actualResult.getPlayerX());
        assertEquals(BattleStatus.WAIT_FOR_MOVE_X, actualResult.getBattleStatus());
    }

    @Test
    void join_whenUserJoinAsPlayerO() {
        User playerO = anyUser(2L);
        Battle battleWithPlayerX = new Battle();
        User playerX = anyUser(1L);
        battleWithPlayerX.setPlayerX(playerX);
        List<Battle> battleList = List.of(battleWithPlayerX);

        Mockito.when(battleRepository.findAllByBattleStatus(Mockito.any())).thenReturn(battleList);
        Mockito.when(battleRepository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));

        Battle actualResult = battleService.join(playerO);

        assertNotNull(actualResult.getPlayerX());
        assertNotEquals(playerO, actualResult.getPlayerX());
        assertEquals(playerO, actualResult.getPlayerO());
        assertEquals(BattleStatus.WAIT_FOR_MOVE_X, actualResult.getBattleStatus());
    }

    @Test
    void getOpenedNow() {
        List<Battle> battleList = List.of(new Battle());

        Mockito.when(battleRepository.findAllByBattleStatus(Mockito.any())).thenReturn(battleList);

        assertEquals(battleList, battleService.getOpenedNow());
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForThrowException")
    void makeMove_assertThrowException(BattleStatus battleStatus,
                                       Class<? extends RuntimeException> classException,
                                       User player) {
        Battle battle = anyBattleWithPlayersWithIds(1L, 2L);

        battle.setBattleStatus(battleStatus);

        Mockito.when(battleRepository.getReferenceById(Mockito.any())).thenReturn(battle);

        assertThrows(classException, () -> battleService.makeMove(anyId(), anyStep(), player));
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForProcessing")
    void makeMove_assertBattleProcessed(User player, BattleStatus battleStatus, BattleStatus expectedBattleStatus) {
        Battle battle = anyBattleWithPlayersWithIds(1L, 2L);
        battle.setBattleStatus(battleStatus);

        Mockito.when(battleRepository.getReferenceById(Mockito.any())).thenReturn(battle);
        Mockito.when(fieldService.move(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(expectedBattleStatus);
        Mockito.when(battleRepository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));

        Battle actualResult = battleService.makeMove(anyId(), anyStep(), player);

        assertEquals(expectedBattleStatus, actualResult.getBattleStatus());
        assertNotNull(actualResult.getLastActivityDatetime());
    }
}

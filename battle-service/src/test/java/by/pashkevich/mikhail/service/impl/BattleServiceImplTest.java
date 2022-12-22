package by.pashkevich.mikhail.service.impl;

import by.pashkevich.mikhail.exception.ForbiddenException;
import by.pashkevich.mikhail.exception.IncorrectDataException;
import by.pashkevich.mikhail.model.User;
import by.pashkevich.mikhail.model.entity.Battle;
import by.pashkevich.mikhail.model.entity.enums.BattleStatus;
import by.pashkevich.mikhail.model.entity.enums.Value;
import by.pashkevich.mikhail.repository.BattleRepository;
import by.pashkevich.mikhail.service.FieldService;
import by.pashkevich.mikhail.service.UserService;
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

    @Mock(lenient = true)
    private UserService userService;

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

    public static Stream<Arguments> getArgumentsForThrowForbiddenException() {
        return Stream.of(
                Arguments.of(anyUser(3L), BattleStatus.WAIT_FOR_MOVE_O),
                Arguments.of(anyUser(3L), BattleStatus.WAIT_FOR_MOVE_X)
        );
    }

    public static Stream<Arguments> getArgumentsForThrowIncorrectDataException() {
        return Stream.of(
                Arguments.of(anyUser(1L), BattleStatus.WAIT_FOR_MOVE_O),
                Arguments.of(anyUser(2L), BattleStatus.WAIT_FOR_MOVE_X),
                Arguments.of(anyUser(), BattleStatus.FINISHED),
                Arguments.of(anyUser(), BattleStatus.INTERRUPTED),
                Arguments.of(anyUser(), BattleStatus.WAIT_FOR_PLAYER)
        );
    }

    @Test
    void create() {
        User anyUser = anyUser();

        Mockito.when(userService.getAuthenticatedUser()).thenReturn(anyUser);
        Mockito.when(fieldService.create()).thenReturn(anyField());
        Mockito.when(battleRepository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));

        Battle actualResult = battleService.create(Value.VALUE_X);

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

        Mockito.when(userService.getAuthenticatedUser()).thenReturn(playerX);
        Mockito.when(battleRepository.findAllByBattleStatus(Mockito.any())).thenReturn(battleList);
        Mockito.when(battleRepository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));

        Battle actualResult = battleService.join();

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

        Mockito.when(userService.getAuthenticatedUser()).thenReturn(playerO);
        Mockito.when(battleRepository.findAllByBattleStatus(Mockito.any())).thenReturn(battleList);
        Mockito.when(battleRepository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));

        Battle actualResult = battleService.join();

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
    @MethodSource("getArgumentsForThrowIncorrectDataException")
    void makeMove_assertThrowIncorrectDataException(User player, BattleStatus battleStatus) {
        Battle battle = anyBattleWithPlayersWithIds(1L, 2L);

        battle.setBattleStatus(battleStatus);

        Mockito.when(battleRepository.getReferenceById(Mockito.any())).thenReturn(battle);
        Mockito.when(userService.getAuthenticatedUser()).thenReturn(player);

        assertThrows(IncorrectDataException.class, () -> battleService.makeMove(anyId(), anyStep()));
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForThrowForbiddenException")
    void makeMove_assertThrowForbiddenException(User player, BattleStatus battleStatus) {
        Battle battle = anyBattleWithPlayersWithIds(1L, 2L);

        battle.setBattleStatus(battleStatus);

        Mockito.when(battleRepository.getReferenceById(Mockito.any())).thenReturn(battle);
        Mockito.when(userService.getAuthenticatedUser()).thenReturn(player);

        assertThrows(ForbiddenException.class, () -> battleService.makeMove(anyId(), anyStep()));
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForProcessing")
    void makeMove_assertBattleProcessed(User player, BattleStatus battleStatus, BattleStatus expectedBattleStatus) {
        Battle battle = anyBattleWithPlayersWithIds(1L, 2L);
        battle.setBattleStatus(battleStatus);

        Mockito.when(battleRepository.getReferenceById(Mockito.any())).thenReturn(battle);
        Mockito.when(userService.getAuthenticatedUser()).thenReturn(player);
        Mockito.when(fieldService.move(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(expectedBattleStatus);
        Mockito.when(battleRepository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));

        Battle actualResult = battleService.makeMove(anyId(), anyStep());

        assertEquals(expectedBattleStatus, actualResult.getBattleStatus());
        assertNotNull(actualResult.getLastActivityDatetime());
    }
}

package by.pashkevich.mikhail.service.impl;

import by.pashkevich.mikhail.entity.Battle;
import by.pashkevich.mikhail.entity.User;
import by.pashkevich.mikhail.entity.enums.BattleStatus;
import by.pashkevich.mikhail.entity.enums.Value;
import by.pashkevich.mikhail.repository.BattleRepository;
import by.pashkevich.mikhail.service.BattleVerifyService;
import by.pashkevich.mikhail.service.FieldService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static by.pashkevich.mikhail.service.CommonMethods.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BattleServiceImplTest {
    @Mock
    private BattleRepository battleRepository;

    @Mock
    private BattleVerifyService battleVerifyService;

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

    @Test
    void create() {
        User anyUser = anyUser();

        when(fieldService.create()).thenReturn(anyField());
        when(battleRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

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

        when(battleRepository.findAllByBattleStatusAndWithoutUser(any(), anyLong())).thenReturn(battleList);
        when(battleRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

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

        when(battleRepository.findAllByBattleStatusAndWithoutUser(any(), anyLong())).thenReturn(battleList);
        when(battleRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Battle actualResult = battleService.join(playerO);

        assertNotNull(actualResult.getPlayerX());
        assertNotEquals(playerO, actualResult.getPlayerX());
        assertEquals(playerO, actualResult.getPlayerO());
        assertEquals(BattleStatus.WAIT_FOR_MOVE_X, actualResult.getBattleStatus());
    }

    @Test
    void getOpenedNow() {
        List<Battle> battleList = List.of(new Battle());

        when(battleRepository.findAllByBattleStatus(any())).thenReturn(battleList);

        assertEquals(battleList, battleService.getOpenedNow());
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForProcessing")
    void makeMove_assertBattleProcessed(User player, BattleStatus battleStatus, BattleStatus expectedBattleStatus) {
        Battle battle = anyBattleWithPlayersWithIds(1L, 2L);
        battle.setBattleStatus(battleStatus);

        when(battleRepository.getReferenceById(any())).thenReturn(battle);
        when(fieldService.move(any(), any(), any())).thenReturn(expectedBattleStatus);
        when(battleRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Battle actualResult = battleService.makeMove(anyId(), anyStep(), player);

        assertEquals(expectedBattleStatus, actualResult.getBattleStatus());
        assertNotNull(actualResult.getLastActivityDatetime());
    }
}

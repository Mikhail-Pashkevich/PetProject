package by.pashkevich.mikhail.service;

import by.pashkevich.mikhail.model.User;
import by.pashkevich.mikhail.model.entity.Battle;
import by.pashkevich.mikhail.model.entity.enums.BattleStatus;
import by.pashkevich.mikhail.repository.BattleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static by.pashkevich.mikhail.service.CommonMethods.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BattleServiceTest {
    @Mock
    private BattleRepository battleRepository;

    @Mock
    private UserService userService;

    @Mock
    private FieldService fieldService;

    @InjectMocks
    private BattleService battleService;


    @Test
    void create() {
        User anyUser = anyUser();

        Mockito.when(userService.getByLogin(Mockito.any())).thenReturn(anyUser);
        Mockito.when(fieldService.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));
        Mockito.when(battleRepository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));

        Battle actualResult = battleService.create(anyUser);

        assertEquals(anyUser, actualResult.getPlayerX());
        assertNull(actualResult.getPlayerO());
        assertNotNull(actualResult.getField());
        assertEquals(BattleStatus.WAIT_FOR_PLAYER, actualResult.getBattleStatus());
    }

    @Test
    void join_whenBattlesExists() {
        User anyUser = anyUser();
        List<Battle> battleList = List.of(new Battle(new User()));

        Mockito.when(userService.getByLogin(Mockito.any())).thenReturn(anyUser);
        Mockito.when(battleRepository.findAllByBattleStatus(Mockito.any())).thenReturn(battleList);
        Mockito.when(battleRepository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));

        Battle actualResult = battleService.join(anyUser);

        assertNotNull(actualResult.getPlayerX());
        assertNotEquals(anyUser, actualResult.getPlayerX());
        assertEquals(anyUser, actualResult.getPlayerO());
        assertEquals(BattleStatus.IN_PROGRESS, actualResult.getBattleStatus());
    }

    @Test
    void join_whenBattlesNotExists() {
        User anyUser = anyUser();
        List<Battle> battleList = List.of();

        Mockito.when(userService.getByLogin(Mockito.any())).thenReturn(anyUser);
        Mockito.when(battleRepository.findAllByBattleStatus(Mockito.any())).thenReturn(battleList);
        Mockito.when(fieldService.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));
        Mockito.when(battleRepository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));

        Battle actualResult = battleService.join(anyUser);

        assertEquals(anyUser, actualResult.getPlayerX());
        assertNull(actualResult.getPlayerO());
        assertNotNull(actualResult.getField());
        assertEquals(BattleStatus.WAIT_FOR_PLAYER, actualResult.getBattleStatus());
    }

    @Test
    void getOpenedNow() {
        List<Battle> battleList = List.of(new Battle());

        Mockito.when(battleRepository.findAllByBattleStatus(Mockito.any())).thenReturn(battleList);

        assertEquals(battleList, battleService.getOpenedNow());
    }

    @ParameterizedTest
    @EnumSource(value = BattleStatus.class, names = {"FINISHED", "WAIT_FOR_PLAYER"})
    void makeMove_whenBattleNotProcessed(BattleStatus battleStatus) {
        Battle battle = new Battle();

        battle.setBattleStatus(battleStatus);

        Mockito.when(battleRepository.findById(Mockito.any())).thenReturn(Optional.of(battle));

        Battle actualResult = battleService.makeMove(battle, anyStep(), anyValue());

        assertEquals(battle, actualResult);
    }

    @ParameterizedTest
    @EnumSource(value = BattleStatus.class, names = {"INTERRUPTED", "IN_PROGRESS", "FINISHED"})
    void makeMove_whenBattleProcessed(BattleStatus battleStatus) {
        Battle battle = new Battle();

        Mockito.when(battleRepository.findById(Mockito.any())).thenReturn(Optional.of(battle));
        Mockito.when(fieldService.move(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(battleStatus);
        Mockito.when(battleRepository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));

        Battle actualResult = battleService.makeMove(battle, anyStep(), anyValue());

        assertEquals(battleStatus, actualResult.getBattleStatus());
        assertNotNull(actualResult.getLastActivityDatetime());
    }
}

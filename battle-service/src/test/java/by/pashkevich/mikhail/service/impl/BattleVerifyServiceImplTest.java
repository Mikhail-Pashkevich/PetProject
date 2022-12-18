package by.pashkevich.mikhail.service.impl;

import by.pashkevich.mikhail.exception.AccessException;
import by.pashkevich.mikhail.exception.BattleUnavailableException;
import by.pashkevich.mikhail.exception.IncorrectDataException;
import by.pashkevich.mikhail.model.User;
import by.pashkevich.mikhail.model.entity.Battle;
import by.pashkevich.mikhail.model.entity.enums.BattleStatus;
import by.pashkevich.mikhail.service.BattleVerifyService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static by.pashkevich.mikhail.service.CommonMethods.anyBattleWithPlayersWithIds;
import static by.pashkevich.mikhail.service.CommonMethods.anyUser;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BattleVerifyServiceImplTest {
    private final BattleVerifyService battleVerifyService = new BattleVerifyServiceImpl();


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

    @ParameterizedTest
    @MethodSource("getArgumentsForThrowException")
    void isAvailableOrThrow(BattleStatus battleStatus,
                            Class<? extends RuntimeException> classException,
                            User player) {
        Battle battle = anyBattleWithPlayersWithIds(1L, 2L);

        battle.setBattleStatus(battleStatus);

        assertThrows(classException, () -> battleVerifyService.isAvailableOrThrow(battle, player));
    }
}

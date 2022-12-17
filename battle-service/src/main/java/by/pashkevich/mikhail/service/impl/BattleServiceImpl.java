package by.pashkevich.mikhail.service.impl;

import by.pashkevich.mikhail.exception.AccessException;
import by.pashkevich.mikhail.exception.BattleUnavailableException;
import by.pashkevich.mikhail.exception.IncorrectDataException;
import by.pashkevich.mikhail.exception.NotFoundException;
import by.pashkevich.mikhail.model.User;
import by.pashkevich.mikhail.model.entity.Battle;
import by.pashkevich.mikhail.model.entity.Field;
import by.pashkevich.mikhail.model.entity.enums.BattleStatus;
import by.pashkevich.mikhail.model.entity.enums.Value;
import by.pashkevich.mikhail.model.util.Step;
import by.pashkevich.mikhail.repository.BattleRepository;
import by.pashkevich.mikhail.service.BattleService;
import by.pashkevich.mikhail.service.FieldService;
import by.pashkevich.mikhail.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BattleServiceImpl implements BattleService {
    private final BattleRepository battleRepository;

    private final FieldService fieldService;
    private final UserService userService;


    @Override
    public Battle create(Value value) {
        Battle battle = new Battle();
        User player = userService.getAuthenticatedUser();
        Field field = fieldService.create();

        setPlayerByValue(battle, player, value);
        battle.setField(field);
        battle.setBattleStatus(BattleStatus.WAIT_FOR_PLAYER);
        battle.setLastActivityDatetime(LocalDateTime.now());

        return battleRepository.save(battle);
    }

    @Override
    public Battle join() {
        User player = userService.getAuthenticatedUser();

        Battle battle = battleRepository.findAllByBattleStatus(BattleStatus.WAIT_FOR_PLAYER)
                .stream()
                .filter(dbBattle -> isExist(dbBattle, player))
                .min(Comparator.comparing(Battle::getLastActivityDatetime))
                .orElseThrow(() -> new NotFoundException("There are no battles without user with id = %d", player.getId()));

        setPlayerOnEmptyPlace(battle, player);
        battle.setBattleStatus(BattleStatus.WAIT_FOR_MOVE_X);
        battle.setLastActivityDatetime(LocalDateTime.now());

        return battleRepository.save(battle);
    }

    @Override
    public List<Battle> getOpenedNow() {
        return battleRepository.findAllByBattleStatus(BattleStatus.WAIT_FOR_PLAYER);
    }

    @Override
    public Battle makeMove(Long battleId, Step step) {
        Battle battle = battleRepository.getReferenceById(battleId);

        if (!battle.getBattleStatus().isActiveBattleStatus()) {
            throw new BattleUnavailableException(battleId);
        }

        User user = userService.getAuthenticatedUser();
        if (isExist(battle, user)) {
            throw new AccessException("User %s can't access to battle with id = %d",
                    user.getLogin(),
                    battle.getId());
        }
        Value value = getValueByUserId(battle, user.getId());

        if (!isCorrectMoveOrder(value, battle.getBattleStatus())) {
            throw new IncorrectDataException("Incorrect move order");
        }

        BattleStatus battleStatus = fieldService.move(battle.getField(), step, value);

        battle.setBattleStatus(battleStatus);
        battle.setLastActivityDatetime(LocalDateTime.now());

        return battleRepository.save(battle);
    }

    private boolean isCorrectMoveOrder(Value value, BattleStatus battleStatus) {
        return (value.equals(Value.VALUE_X) && battleStatus.equals(BattleStatus.WAIT_FOR_MOVE_X))
                || (value.equals(Value.VALUE_O) && battleStatus.equals(BattleStatus.WAIT_FOR_MOVE_O));
    }

    private void setPlayerByValue(Battle battle, User player, Value value) {
        switch (value) {
            case VALUE_X -> battle.setPlayerX(player);
            case VALUE_O -> battle.setPlayerO(player);
            default -> throw new IncorrectDataException("Can't process value: %s", value.name());
        }
    }

    private void setPlayerOnEmptyPlace(Battle battle, User player) {
        if (battle.getPlayerO() == null) {
            battle.setPlayerO(player);
        } else {
            battle.setPlayerX(player);
        }
    }

    private Value getValueByUserId(Battle battle, Long userId) {
        if (battle.getPlayerX().getId().equals(userId)) {
            return Value.VALUE_X;
        } else if (battle.getPlayerO().getId().equals(userId)) {
            return Value.VALUE_O;
        } else {
            throw new IncorrectDataException("Can't process userId: %d", userId);
        }
    }

    private boolean isExist(Battle battle, User player) {
        return player.equals(battle.getPlayerX()) || player.equals(battle.getPlayerO());
    }
}

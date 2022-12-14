package by.pashkevich.mikhail.service.impl;

import by.pashkevich.mikhail.exception.ForbiddenException;
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

        battle.setPlayerByValue(player, value);
        battle.setField(field);
        battle.setBattleStatus(BattleStatus.WAIT_FOR_PLAYER);
        battle.setLastActivityDatetime(LocalDateTime.now());

        return battleRepository.save(battle);
    }

    @Override
    public Battle join() {
        User player = userService.getAuthenticatedUser();

        Battle joinBattle = battleRepository.findAllByBattleStatus(BattleStatus.WAIT_FOR_PLAYER)
                .stream()
                .filter(battle -> !battle.isExist(player))
                .min(Comparator.comparing(Battle::getLastActivityDatetime))
                .orElseThrow(() -> new NotFoundException("There are any battles without user with id = " + player.getId()));

        joinBattle.setPlayerOnEmptyPlace(player);
        joinBattle.setBattleStatus(BattleStatus.WAIT_FOR_MOVE_X);
        joinBattle.setLastActivityDatetime(LocalDateTime.now());

        return battleRepository.save(joinBattle);
    }

    @Override
    public List<Battle> getOpenedNow() {
        return battleRepository.findAllByBattleStatus(BattleStatus.WAIT_FOR_PLAYER);
    }

    @Override
    public Battle makeMove(Long battleId, Step step) {
        Battle battle = battleRepository.getReferenceById(battleId);

        if (!battle.getBattleStatus().isActiveBattleStatus()) {
            throw new IncorrectDataException("Battle with id = " + battleId + " unavailable");
        }

        User user = userService.getAuthenticatedUser();
        if (!battle.isExist(user)) {
            throw new ForbiddenException("User " + user.getLogin() + " can't access to battle with id = " + battle.getId());
        }
        Value value = battle.getValueByUserId(user.getId());

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
}

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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BattleServiceImpl implements BattleService {
    private final BattleRepository battleRepository;

    private final FieldService fieldService;


    @Override
    public Battle create(Value value, User user) {
        Battle battle = new Battle();
        Field field = fieldService.create();

        setPlayerByValue(battle, user, value);
        battle.setField(field);
        battle.setBattleStatus(BattleStatus.WAIT_FOR_PLAYER);
        battle.setLastActivityDatetime(LocalDateTime.now());

        return battleRepository.save(battle);
    }

    @Override
    public Battle join(User player) {
        Battle battle = battleRepository.findAllByBattleStatusAndWithoutUser(BattleStatus.WAIT_FOR_PLAYER, player.getId())
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("There are no battles without player with id = %d", player.getId()));

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
    public Battle makeMove(Long battleId, Step step, User player) {
        Battle battle = battleRepository.getReferenceById(battleId);

        if (!battle.getBattleStatus().isActiveBattleStatus()) {
            throw new BattleUnavailableException(battleId);
        }

        if (!isExist(battle, player)) {
            throw new AccessException("User %s can't access to battle with id = %d",
                    player.getLogin(),
                    battle.getId());
        }
        Value value = getValueByUserId(battle, player.getId());

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

package by.pashkevich.mikhail.service.util;

import by.pashkevich.mikhail.exception.IncorrectDataException;
import by.pashkevich.mikhail.model.User;
import by.pashkevich.mikhail.model.entity.Battle;
import by.pashkevich.mikhail.model.entity.enums.BattleStatus;
import by.pashkevich.mikhail.model.entity.enums.Value;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BattleUtil {

    public boolean isCorrectMoveOrder(Value value, BattleStatus battleStatus) {
        return (value.equals(Value.VALUE_X) && battleStatus.equals(BattleStatus.WAIT_FOR_MOVE_X))
                || (value.equals(Value.VALUE_O) && battleStatus.equals(BattleStatus.WAIT_FOR_MOVE_O));
    }

    public void setPlayerByValue(Battle battle, User player, Value value) {
        switch (value) {
            case VALUE_X -> battle.setPlayerX(player);
            case VALUE_O -> battle.setPlayerO(player);
            default -> throw new IncorrectDataException("Can't process value: %s", value.name());
        }
    }

    public void setPlayerOnEmptyPlace(Battle battle, User player) {
        if (battle.getPlayerO() == null) {
            battle.setPlayerO(player);
        } else {
            battle.setPlayerX(player);
        }
    }

    public Value getValueByUserId(Battle battle, Long userId) {
        if (battle.getPlayerX().getId().equals(userId)) {
            return Value.VALUE_X;
        } else if (battle.getPlayerO().getId().equals(userId)) {
            return Value.VALUE_O;
        } else {
            throw new IncorrectDataException("Can't process userId: %d", userId);
        }
    }

    public boolean isExist(Battle battle, User player) {
        return player.equals(battle.getPlayerX()) || player.equals(battle.getPlayerO());
    }
}

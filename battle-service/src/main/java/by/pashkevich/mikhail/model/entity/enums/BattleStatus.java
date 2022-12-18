package by.pashkevich.mikhail.model.entity.enums;

import java.util.List;

public enum BattleStatus {
    WAIT_FOR_PLAYER,
    WAIT_FOR_MOVE_X,
    WAIT_FOR_MOVE_O,
    FINISHED,
    INTERRUPTED;


    private static final List<BattleStatus> ACTIVE_BATTLES = List.of(WAIT_FOR_MOVE_X, WAIT_FOR_MOVE_O);

    public boolean isActiveBattleStatus() {
        return ACTIVE_BATTLES.contains(this);
    }
}

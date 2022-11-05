package by.pashkevich.mikhail.model.entity.enums;

import java.util.List;

public enum BattleStatus {
    WAIT_FOR_PLAYER,
    IN_PROGRESS,
    FINISHED,
    INTERRUPTED;


    private static final List<BattleStatus> ACTIVE_BATTLE = List.of(IN_PROGRESS);

    public boolean isActiveBattleStatus() {
        return ACTIVE_BATTLE.contains(this);
    }
}

package by.pashkevich.mikhail.service;

import by.pashkevich.mikhail.model.entity.Battle;
import by.pashkevich.mikhail.model.entity.enums.Value;

import java.util.List;

public interface BattleService {
    Battle create(Long id, Value value);

    Battle join(Long playerId);

    List<Battle> getOpenedNow();

    Battle makeMove(Long battleId, Integer step, Value value);
}
package by.pashkevich.mikhail.service;

import by.pashkevich.mikhail.model.entity.Battle;
import by.pashkevich.mikhail.model.entity.enums.Value;
import by.pashkevich.mikhail.model.util.Step;

import java.util.List;

public interface BattleService {
    Battle create(Long id, Value value);

    Battle join(Long playerId);

    List<Battle> getOpenedNow();

    Battle makeMove(Long battleId, Step step, Value value);
}
package by.pashkevich.mikhail.service;

import by.pashkevich.mikhail.model.entity.Battle;
import by.pashkevich.mikhail.model.entity.Step;
import by.pashkevich.mikhail.model.entity.User;
import by.pashkevich.mikhail.model.entity.enums.Value;

import java.util.List;

public interface BattleService {
    Battle create(Value value, User user);

    Battle join(User user);

    List<Battle> getOpenedNow();

    Battle makeMove(Long battleId, Step step, User user);
}

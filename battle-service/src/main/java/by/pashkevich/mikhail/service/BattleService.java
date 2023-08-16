package by.pashkevich.mikhail.service;

import by.pashkevich.mikhail.entity.Battle;
import by.pashkevich.mikhail.entity.Step;
import by.pashkevich.mikhail.entity.User;
import by.pashkevich.mikhail.entity.enums.Value;

import java.util.List;

public interface BattleService {
    Battle create(Value value, User user);

    Battle join(User user);

    List<Battle> getOpenedNow();

    Battle makeMove(Long battleId, Step step, User user);
}

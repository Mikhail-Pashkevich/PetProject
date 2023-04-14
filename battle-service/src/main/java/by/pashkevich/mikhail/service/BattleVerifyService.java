package by.pashkevich.mikhail.service;

import by.pashkevich.mikhail.model.entity.Battle;
import by.pashkevich.mikhail.model.entity.User;

public interface BattleVerifyService {
    void isAvailableOrThrow(Battle battle, User user);
}

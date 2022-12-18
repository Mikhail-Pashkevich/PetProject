package by.pashkevich.mikhail.service;

import by.pashkevich.mikhail.model.User;
import by.pashkevich.mikhail.model.entity.Battle;

public interface BattleVerifyService {
    void isAvailableOrThrow(Battle battle, User user);
}

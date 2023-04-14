package by.pashkevich.mikhail.service;

import by.pashkevich.mikhail.entity.Battle;
import by.pashkevich.mikhail.entity.User;

public interface BattleVerifyService {
    void isAvailableOrThrow(Battle battle, User user);
}

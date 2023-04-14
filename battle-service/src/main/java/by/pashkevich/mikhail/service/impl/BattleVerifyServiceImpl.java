package by.pashkevich.mikhail.service.impl;

import by.pashkevich.mikhail.model.entity.Battle;
import by.pashkevich.mikhail.model.entity.User;
import by.pashkevich.mikhail.model.entity.enums.Value;
import by.pashkevich.mikhail.exception.AccessException;
import by.pashkevich.mikhail.exception.BattleUnavailableException;
import by.pashkevich.mikhail.exception.IncorrectDataException;
import by.pashkevich.mikhail.service.BattleVerifyService;
import by.pashkevich.mikhail.service.util.BattleUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BattleVerifyServiceImpl implements BattleVerifyService {

    @Override
    public void isAvailableOrThrow(Battle battle, User user) {
        if (!battle.getBattleStatus().isActiveBattleStatus()) {
            throw new BattleUnavailableException(battle.getId());
        }

        if (!BattleUtil.isExist(battle, user)) {
            throw new AccessException("User %s can't access to battle with id = %d",
                    user.getLogin(),
                    battle.getId());
        }

        Value value = BattleUtil.getValueByUserId(battle, user.getId());
        if (!BattleUtil.isCorrectMoveOrder(value, battle.getBattleStatus())) {
            throw new IncorrectDataException("Incorrect move order");
        }
    }
}

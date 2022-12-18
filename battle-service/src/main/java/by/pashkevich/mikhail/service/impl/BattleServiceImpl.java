package by.pashkevich.mikhail.service.impl;

import by.pashkevich.mikhail.exception.NotFoundException;
import by.pashkevich.mikhail.model.User;
import by.pashkevich.mikhail.model.entity.Battle;
import by.pashkevich.mikhail.model.entity.Field;
import by.pashkevich.mikhail.model.entity.enums.BattleStatus;
import by.pashkevich.mikhail.model.entity.enums.Value;
import by.pashkevich.mikhail.model.util.Step;
import by.pashkevich.mikhail.repository.BattleRepository;
import by.pashkevich.mikhail.service.BattleService;
import by.pashkevich.mikhail.service.BattleVerifyService;
import by.pashkevich.mikhail.service.FieldService;
import by.pashkevich.mikhail.service.util.BattleUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BattleServiceImpl implements BattleService {
    private final BattleRepository battleRepository;

    private final BattleVerifyService battleVerifyService;
    private final FieldService fieldService;


    @Override
    public Battle create(Value value, User user) {
        Battle battle = new Battle();
        Field field = fieldService.create();

        BattleUtil.setPlayerByValue(battle, user, value);
        battle.setField(field);
        battle.setBattleStatus(BattleStatus.WAIT_FOR_PLAYER);
        battle.setLastActivityDatetime(LocalDateTime.now());

        return battleRepository.save(battle);
    }

    @Override
    public Battle join(User player) {
        Battle battle = battleRepository.findAllByBattleStatusAndWithoutUser(BattleStatus.WAIT_FOR_PLAYER, player.getId())
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("There are no battles without player with id = %d", player.getId()));

        BattleUtil.setPlayerOnEmptyPlace(battle, player);
        battle.setBattleStatus(BattleStatus.WAIT_FOR_MOVE_X);
        battle.setLastActivityDatetime(LocalDateTime.now());

        return battleRepository.save(battle);
    }

    @Override
    public List<Battle> getOpenedNow() {
        return battleRepository.findAllByBattleStatus(BattleStatus.WAIT_FOR_PLAYER);
    }

    @Override
    public Battle makeMove(Long battleId, Step step, User player) {
        Battle battle = battleRepository.getReferenceById(battleId);

        battleVerifyService.isAvailableOrThrow(battle, player);

        Value value = BattleUtil.getValueByUserId(battle, player.getId());
        BattleStatus battleStatus = fieldService.move(battle.getField(), step, value);

        battle.setBattleStatus(battleStatus);
        battle.setLastActivityDatetime(LocalDateTime.now());

        return battleRepository.save(battle);
    }
}

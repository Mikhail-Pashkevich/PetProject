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
import by.pashkevich.mikhail.service.FieldService;
import by.pashkevich.mikhail.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BattleServiceImpl implements BattleService {
    private final BattleRepository battleRepository;

    private final FieldService fieldService;
    private final UserService userService;


    @Override
    public Battle create(Long id, Value value) {
        Battle battle = new Battle();
        User player = userService.getById(id);
        Field field = fieldService.create();

        battle.setPlayerByValue(player, value);
        battle.setField(field);
        battle.setBattleStatus(BattleStatus.WAIT_FOR_PLAYER);
        battle.setLastActivityDatetime(LocalDateTime.now());

        return battleRepository.save(battle);
    }

    @Override
    public Battle join(Long playerId) {
        User player = userService.getById(playerId);

        Battle battle = battleRepository.findAllByBattleStatus(BattleStatus.WAIT_FOR_PLAYER)
                .stream()
                .min(Comparator.comparing(Battle::getLastActivityDatetime))
                .orElseThrow(() -> {
                    throw new NotFoundException("There any battle with battleStatus = " + BattleStatus.WAIT_FOR_PLAYER);
                });

        if (battle.getPlayerO() == null) {
            battle.setPlayerO(player);
        } else {
            battle.setPlayerX(player);
        }
        battle.setBattleStatus(BattleStatus.IN_PROGRESS);

        return battleRepository.save(battle);
    }

    @Override
    public List<Battle> getOpenedNow() {
        return battleRepository.findAllByBattleStatus(BattleStatus.WAIT_FOR_PLAYER);
    }

    @Override
    public Battle makeMove(Long battleId, Step step, Value value) {
        Battle battle = battleRepository.getReferenceById(battleId);

        if (!battle.getBattleStatus().isActiveBattleStatus()) {
            return battle;
        }

        BattleStatus battleStatus = fieldService.move(battle.getField(), step, value);

        battle.setBattleStatus(battleStatus);
        battle.setLastActivityDatetime(LocalDateTime.now());

        return battleRepository.save(battle);
    }
}

package by.pashkevich.mikhail.service;

import by.pashkevich.mikhail.model.User;
import by.pashkevich.mikhail.model.entity.Battle;
import by.pashkevich.mikhail.model.entity.Field;
import by.pashkevich.mikhail.model.entity.enums.BattleStatus;
import by.pashkevich.mikhail.model.entity.enums.Value;
import by.pashkevich.mikhail.repository.BattleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static by.pashkevich.mikhail.model.entity.enums.BattleStatus.FINISHED;
import static by.pashkevich.mikhail.model.entity.enums.BattleStatus.WAIT_FOR_PLAYER;

@Service
@RequiredArgsConstructor
public class BattleServiceImpl implements BattleService {
    private final BattleRepository battleRepository;

    private final FieldService fieldService;
    private final UserService userService;


    @Override
    public Battle create(Long id, Value value) {
        User player = userService.getById(id);

        Battle battle = new Battle();


        battle.setBattleStatus(BattleStatus.WAIT_FOR_PLAYER);
        battle.setLastActivityDatetime(LocalDateTime.now());

        switch (value) {
            case VALUE_X -> battle.setPlayerX(player);
            case VALUE_O -> battle.setPlayerO(player);
            default -> throw new IllegalArgumentException("Can't process value: " + value);
        }

        Field field = fieldService.save(new Field());

        battle.setField(field);

        battle = battleRepository.save(battle);

        return battle;
    }


    @Override
    public Battle join(Long playerId) {
        User player = userService.getById(playerId);

        Battle battle = battleRepository.findAllByBattleStatus(BattleStatus.WAIT_FOR_PLAYER)
                .stream()
                .min(Comparator.comparing(Battle::getLastActivityDatetime))
                .orElseThrow(() -> {
                    throw new UnsupportedOperationException("Not implemented yet!");
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
        return battleRepository.findAllByBattleStatus(WAIT_FOR_PLAYER);
    }


    @Override
    public Battle makeMove(Long battleId, Integer step, Value value) {
        Battle battle = battleRepository.findById(battleId).orElseThrow(() -> {
            throw new UnsupportedOperationException("Not implemented yet!");
        });

        if (FINISHED.equals(battle.getBattleStatus()) || WAIT_FOR_PLAYER.equals(battle.getBattleStatus())) {
            return battle;
        }

        BattleStatus battleStatus = fieldService.move(battle.getField(), step, value);

        battle.setBattleStatus(battleStatus);
        battle.setLastActivityDatetime(LocalDateTime.now());


        return battleRepository.save(battle);
    }
}

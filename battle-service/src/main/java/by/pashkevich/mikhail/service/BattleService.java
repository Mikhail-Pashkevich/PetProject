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

import static by.pashkevich.mikhail.model.entity.enums.BattleStatus.*;

@Service
@RequiredArgsConstructor
public class BattleService {
    private final BattleRepository battleRepository;

    private final FieldService fieldService;
    private final UserService userService;


    public Battle create(User player) {
        player = userService.getByLogin(player.getLogin());

        return createAndInsert(player);
    }

    public Battle join(User player) {
        player = userService.getByLogin(player.getLogin());

        Battle battle = battleRepository.findAllByBattleStatus(WAIT_FOR_PLAYER)
                .stream()
                .min(Comparator.comparing(Battle::getLastActivityDatetime))
                .orElse(createAndInsert(player));

        if (!battle.getPlayerX().equals(player)) {
            battle.setPlayerO(player);
            battle.setBattleStatus(IN_PROGRESS);

            return battleRepository.save(battle);
        }

        return battle;
    }

    public List<Battle> getOpenedNow() {
        return battleRepository.findAllByBattleStatus(WAIT_FOR_PLAYER);
    }

    public Battle makeMove(Battle battle, Integer step, Value value) {
        battle = battleRepository.findById(battle.getId()).orElseThrow(() -> {
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

    private Battle createAndInsert(User player) {
        Battle battle = new Battle(player);

        Field field = fieldService.save(new Field());

        battle.setField(field);

        return battleRepository.save(battle);
    }
}

package by.pashkevich.mikhail.service;

import by.pashkevich.mikhail.model.User;
import by.pashkevich.mikhail.model.dto.BattleDto;
import by.pashkevich.mikhail.model.entity.Battle;
import by.pashkevich.mikhail.model.entity.Field;
import by.pashkevich.mikhail.model.entity.enums.BattleStatus;
import by.pashkevich.mikhail.model.entity.enums.Value;
import by.pashkevich.mikhail.repository.BattleRepository;
import by.pashkevich.mikhail.repository.FieldRepository;
import by.pashkevich.mikhail.service.mapper.BattleListMapper;
import by.pashkevich.mikhail.service.mapper.BattleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BattleService {
    private final BattleRepository battleRepository;
    private final FieldRepository fieldRepository;

    private final FieldService fieldService;
    private final UserService userService;

    private final BattleMapper battleMapper;
    private final BattleListMapper battleListMapper;


    public BattleDto create(Long id, Value value) {
        User player = userService.getById(id);

        Battle battle = new Battle();

        battle.setBattleStatus(BattleStatus.WAIT_FOR_PLAYER);
        battle.setLastActivityDatetime(LocalDateTime.now());

        switch (value) {
            case VALUE_X -> battle.setPlayerX(player);
            case VALUE_O -> battle.setPlayerO(player);
            default -> throw new IllegalArgumentException("Can't process value: " + value);
        }

        Field field = fieldRepository.save(new Field());

        battle.setField(field);

        battle = battleRepository.save(battle);

        return battleMapper.toBattleDto(battle);
    }

    public BattleDto join(Long id) {
        User player = userService.getById(id);

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

        battleRepository.save(battle);

        return battleMapper.toBattleDto(battle);
    }

    public List<BattleDto> getOpenedNow() {
        List<Battle> battleList = battleRepository.findAllByBattleStatus(BattleStatus.WAIT_FOR_PLAYER);

        return battleListMapper.toBattleDtoList(battleList);
    }

    public BattleDto makeMove(Long battleId, Integer step, Value value) {
        Battle battle = battleRepository.findById(battleId).orElseThrow(() -> {
            throw new UnsupportedOperationException("Not implemented yet!");
        });

        if (battle.getBattleStatus().equals(BattleStatus.FINISHED)) {
            return battleMapper.toBattleDto(battle);
        }

        BattleStatus battleStatus = fieldService.move(battle.getField(), step, value);

        battle.setBattleStatus(battleStatus);
        battle.setLastActivityDatetime(LocalDateTime.now());

        battleRepository.save(battle);

        return battleMapper.toBattleDto(battle);
    }
}

package by.pashkevich.mikhail.service;

import by.pashkevich.mikhail.model.User;
import by.pashkevich.mikhail.model.dto.BattleDto;
import by.pashkevich.mikhail.model.dto.PlayerDto;
import by.pashkevich.mikhail.model.entity.Battle;
import by.pashkevich.mikhail.model.entity.Field;
import by.pashkevich.mikhail.model.entity.enums.BattleStatus;
import by.pashkevich.mikhail.model.entity.enums.Value;
import by.pashkevich.mikhail.repository.BattleRepository;
import by.pashkevich.mikhail.repository.FieldRepository;
import by.pashkevich.mikhail.service.mapper.BattleListMapper;
import by.pashkevich.mikhail.service.mapper.BattleMapper;
import by.pashkevich.mikhail.service.mapper.PlayerMapper;
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
    private final PlayerMapper playerMapper;


    public BattleDto create(PlayerDto playerDto) {
        User player = playerMapper.toUser(playerDto);
        player = userService.getByLogin(player.getLogin());

        Battle battle = createAndInsert(player);

        return battleMapper.toBattleDto(battle);
    }

    public BattleDto join(PlayerDto playerDto) {
        User player = playerMapper.toUser(playerDto);
        player = userService.getByLogin(player.getLogin());

        Battle battle = battleRepository.findAllByBattleStatus(BattleStatus.WAIT_FOR_PLAYER)
                .stream()
                .min(Comparator.comparing(Battle::getLastActivityDatetime))
                .orElse(createAndInsert(player));

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

    public BattleDto makeMove(BattleDto battleDto, Integer step, Value value) {
        Battle battle = battleMapper.toBattle(battleDto);
        battle = battleRepository.findById(battle.getId()).orElseThrow(() -> {
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

    private Battle createAndInsert(User player) {
        Battle battle = new Battle(player);

        Field field = fieldRepository.save(new Field());

        battle.setField(field);

        return battleRepository.save(battle);
    }
}

package by.pashkevich.mikhail.controller;

import by.pashkevich.mikhail.model.User;
import by.pashkevich.mikhail.model.dto.BattleDto;
import by.pashkevich.mikhail.model.dto.PlayerDto;
import by.pashkevich.mikhail.model.entity.Battle;
import by.pashkevich.mikhail.model.entity.enums.Value;
import by.pashkevich.mikhail.service.BattleService;
import by.pashkevich.mikhail.service.mapper.BattleMapper;
import by.pashkevich.mikhail.service.mapper.PlayerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/battle")
@RequiredArgsConstructor
public class BattleController {
    private final BattleService battleService;

    private final PlayerMapper playerMapper;
    private final BattleMapper battleMapper;


    @PostMapping(headers = "create")
    public BattleDto create(@RequestBody PlayerDto playerDto) {
        User user = playerMapper.toUser(playerDto);

        Battle battle = battleService.create(user);

        return battleMapper.toBattleDto(battle);
    }

    @PostMapping(headers = "join")
    public BattleDto join(@RequestBody PlayerDto playerDto) {
        User user = playerMapper.toUser(playerDto);

        Battle battle = battleService.join(user);

        return battleMapper.toBattleDto(battle);
    }

    @PostMapping(headers = {"move", "value"})
    public BattleDto makeMove(@RequestBody BattleDto battleDto,
                              @RequestHeader("move") Integer step,
                              @RequestHeader("value") Value value) {
        Battle battle = battleMapper.toBattle(battleDto);

        battle = battleService.makeMove(battle, step, value);

        return battleMapper.toBattleDto(battle);
    }

    @GetMapping
    public List<BattleDto> openedNow() {
        List<Battle> battleList = battleService.getOpenedNow();

        return battleMapper.toBattleDtoList(battleList);
    }
}

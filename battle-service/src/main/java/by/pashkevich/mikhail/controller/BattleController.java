package by.pashkevich.mikhail.controller;

import by.pashkevich.mikhail.model.dto.BattleDto;
import by.pashkevich.mikhail.model.dto.CreateDto;
import by.pashkevich.mikhail.model.dto.MoveDto;
import by.pashkevich.mikhail.model.entity.Battle;
import by.pashkevich.mikhail.model.util.Step;
import by.pashkevich.mikhail.service.BattleService;
import by.pashkevich.mikhail.service.mapper.BattleMapper;
import by.pashkevich.mikhail.service.mapper.MoveMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/battle")
@RequiredArgsConstructor
public class BattleController {
    private final BattleService battleService;

    private final BattleMapper battleMapper;
    private final MoveMapper moveMapper;


    @PostMapping
    public BattleDto create(@RequestBody CreateDto createDto) {
        Battle battle = battleService.create(createDto.getUserId(), createDto.getValue());

        return battleMapper.toBattleDto(battle);
    }

    @PostMapping("/join/{id}")
    public BattleDto join(@PathVariable Long id) {

        Battle battle = battleService.join(id);

        return battleMapper.toBattleDto(battle);
    }

    @PostMapping("/move")
    public BattleDto makeMove(@RequestBody MoveDto moveDto) {
        Step step = moveMapper.toStep(moveDto);

        Battle battle = battleService.makeMove(moveDto.getBattleId(), step, moveDto.getUserId());

        return battleMapper.toBattleDto(battle);
    }

    @GetMapping
    public List<BattleDto> openedNow() {
        List<Battle> battleList = battleService.getOpenedNow();

        return battleMapper.toBattleDtoList(battleList);
    }
}

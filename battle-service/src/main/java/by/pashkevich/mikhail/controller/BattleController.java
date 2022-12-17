package by.pashkevich.mikhail.controller;

import by.pashkevich.mikhail.aspect.AuthUserProvide;
import by.pashkevich.mikhail.model.User;
import by.pashkevich.mikhail.model.dto.BattleDto;
import by.pashkevich.mikhail.model.dto.CreateDto;
import by.pashkevich.mikhail.model.dto.MoveDto;
import by.pashkevich.mikhail.model.entity.Battle;
import by.pashkevich.mikhail.model.util.Step;
import by.pashkevich.mikhail.service.BattleService;
import by.pashkevich.mikhail.service.StatisticService;
import by.pashkevich.mikhail.service.mapper.BattleMapper;
import by.pashkevich.mikhail.service.mapper.MoveMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/battle")
@RequiredArgsConstructor
public class BattleController {
    private final StatisticService statisticService;
    private final BattleService battleService;

    private final BattleMapper battleMapper;
    private final MoveMapper moveMapper;


    @PostMapping
    @AuthUserProvide
    public BattleDto create(@Valid @RequestBody CreateDto createDto, User user) {
        Battle battle = battleService.create(createDto.getValue(), user);

        return battleMapper.toBattleDto(battle);
    }

    @PostMapping("/join")
    @AuthUserProvide
    public BattleDto join(User user) {

        Battle battle = battleService.join(user);

        return battleMapper.toBattleDto(battle);
    }

    @PostMapping("/move")
    @AuthUserProvide
    public BattleDto makeMove(@Valid @RequestBody MoveDto moveDto, User user) {
        Step step = moveMapper.toStep(moveDto);

        Battle battle = battleService.makeMove(moveDto.getBattleId(), step, user);

        statisticService.save();

        return battleMapper.toBattleDto(battle);
    }

    @GetMapping
    public List<BattleDto> openedNow() {
        List<Battle> battleList = battleService.getOpenedNow();

        return battleMapper.toBattleDtoList(battleList);
    }
}

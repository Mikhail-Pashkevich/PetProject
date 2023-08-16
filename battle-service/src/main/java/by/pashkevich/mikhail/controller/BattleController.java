package by.pashkevich.mikhail.controller;

import by.pashkevich.mikhail.client.StatisticClient;
import by.pashkevich.mikhail.dto.BattleDto;
import by.pashkevich.mikhail.dto.CreateDto;
import by.pashkevich.mikhail.dto.MoveDto;
import by.pashkevich.mikhail.entity.Battle;
import by.pashkevich.mikhail.entity.Step;
import by.pashkevich.mikhail.entity.User;
import by.pashkevich.mikhail.mapper.BattleMapper;
import by.pashkevich.mikhail.mapper.MoveMapper;
import by.pashkevich.mikhail.service.BattleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/battle")
@RequiredArgsConstructor
public class BattleController {
    private final StatisticClient statisticClient;

    private final BattleService battleService;

    private final BattleMapper battleMapper;
    private final MoveMapper moveMapper;


    @PostMapping
    public BattleDto create(@Valid @RequestBody CreateDto createDto, @AuthenticationPrincipal User user) {
        Battle battle = battleService.create(createDto.getValue(), user);

        return battleMapper.toBattleDto(battle);
    }

    @PostMapping("/join")
    public BattleDto join(@AuthenticationPrincipal User user) {

        Battle battle = battleService.join(user);

        return battleMapper.toBattleDto(battle);
    }

    @PostMapping("/move")
    public BattleDto makeMove(@Valid @RequestBody MoveDto moveDto, @AuthenticationPrincipal User user) {
        Step step = moveMapper.toStep(moveDto);

        Battle battle = battleService.makeMove(moveDto.getBattleId(), step, user);

        statisticClient.save();

        return battleMapper.toBattleDto(battle);
    }

    @GetMapping
    public List<BattleDto> openedNow() {
        List<Battle> battleList = battleService.getOpenedNow();

        return battleMapper.toBattleDtoList(battleList);
    }
}

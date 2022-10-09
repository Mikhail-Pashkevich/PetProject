package by.pashkevich.mikhail.controller;

import by.pashkevich.mikhail.model.dto.BattleDto;
import by.pashkevich.mikhail.model.dto.PlayerDto;
import by.pashkevich.mikhail.model.entity.enums.Value;
import by.pashkevich.mikhail.service.BattleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/battle")
@RequiredArgsConstructor
public class BattleController {
    private final BattleService battleService;


    @PostMapping(headers = "create")
    public BattleDto create(@RequestBody PlayerDto playerDto) {
        return battleService.create(playerDto);
    }

    @PostMapping(headers = "join")
    public BattleDto join(@RequestBody PlayerDto playerDto) {
        return battleService.join(playerDto);
    }

    @PostMapping(headers = {"move", "value"})
    public BattleDto makeMove(@RequestBody BattleDto battleDto,
                              @RequestHeader("move") Integer step,
                              @RequestHeader("value") Value value) {
        return battleService.makeMove(battleDto, step, value);
    }

    @GetMapping
    public List<BattleDto> openedNow() {
        return battleService.getOpenedNow();
    }
}

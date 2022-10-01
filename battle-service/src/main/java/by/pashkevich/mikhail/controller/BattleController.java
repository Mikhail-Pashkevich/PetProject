package by.pashkevich.mikhail.controller;

import by.pashkevich.mikhail.model.dto.BattleDto;
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
    public BattleDto create() {
        return battleService.create();
    }

    @PostMapping(headers = "join")
    public BattleDto join() {
        return battleService.join();
    }

    @PostMapping(headers = "move")
    public BattleDto makeMove(@RequestBody BattleDto battleDto, @RequestHeader("move") Integer step) {
        return battleService.makeMove(battleDto, step);
    }

    @GetMapping
    public List<BattleDto> openedNow() {
        return battleService.getOpenedNow();
    }
}

package by.pashkevich.mikhail.controller;

import by.pashkevich.mikhail.model.dto.BattleDto;
import by.pashkevich.mikhail.model.dto.CreateDto;
import by.pashkevich.mikhail.model.dto.MoveDto;
import by.pashkevich.mikhail.service.BattleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/battle")
@RequiredArgsConstructor
public class BattleController {
    private final BattleService battleService;


    @PostMapping
    public BattleDto create(@RequestBody CreateDto createDto) {
        return battleService.create(createDto.getUserId(), createDto.getValue());
    }

    @PostMapping("/join/{id}")
    public BattleDto join(@PathVariable Long id) {
        return battleService.join(id);
    }

    @PostMapping("move")
    public BattleDto makeMove(@RequestBody MoveDto moveDto) {
        return battleService.makeMove(moveDto.getBattleId(), moveDto.getStep(), moveDto.getValue());
    }

    @GetMapping
    public List<BattleDto> openedNow() {
        return battleService.getOpenedNow();
    }
}

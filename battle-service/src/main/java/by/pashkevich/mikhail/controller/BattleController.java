package by.pashkevich.mikhail.controller;

import by.pashkevich.mikhail.model.dto.BattleDto;
import by.pashkevich.mikhail.model.dto.StepDto;
import by.pashkevich.mikhail.service.BattleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/battle")
@RequiredArgsConstructor
public class BattleController {

    private final BattleService battleService;


    @GetMapping("/new-battle")
    public BattleDto newBattle() {
        return battleService.createNewBattle();
    }

    @GetMapping("/connect-to-battle")
    public BattleDto connectToBattle() {
        return battleService.connectToBattle();
    }

    @PostMapping("/make-move")
    public BattleDto makeMove(BattleDto battleDto, StepDto stepDto) {
        return battleService.makeMove(battleDto, stepDto);
    }

    @GetMapping("/opened-now")
    public List<BattleDto> openedNow() {
        return battleService.getOpenBattles();
    }
}

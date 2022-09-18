package by.pashkevich.mikhail.controller;

import by.pashkevich.mikhail.model.Battle;
import by.pashkevich.mikhail.model.Field;
import by.pashkevich.mikhail.model.Step;
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
    public Battle newBattle() {
        return battleService.createNewBattle();
    }

    @GetMapping("/exist-battle")
    public Battle existBattle() {
        return battleService.getExistBattle();
    }

    @PostMapping("/make-move")
    public Battle makeMove(Battle battle, Step step) {
        return battleService.makeMove(battle, step);
    }

    @GetMapping("/opened-now")
    public List<Battle> openedNow() {
        return battleService.getOpenBattles();
    }
}

package by.pashkevich.mikhail.service;

import by.pashkevich.mikhail.model.Battle;
import by.pashkevich.mikhail.model.Step;
import by.pashkevich.mikhail.repository.BattleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BattleService {

    private final BattleRepository battleRepository;

    private final FieldService fieldService;


    public Battle createNewBattle() {
        return null;
    }

    public Battle getExistBattle() {
        return null;
    }

    public List<Battle> getOpenBattles() {
        return null;
    }

    public Battle makeMove(Battle battle, Step step) {
        //get value from authorized user
        fieldService.move(battle.getField(), step, null);

        return null;
    }
}

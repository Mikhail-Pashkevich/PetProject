package by.pashkevich.mikhail.service;

import by.pashkevich.mikhail.model.dto.BattleDto;
import by.pashkevich.mikhail.model.dto.StepDto;
import by.pashkevich.mikhail.repository.BattleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BattleService {

    private final BattleRepository battleRepository;

    private final FieldService fieldService;


    public BattleDto createNewBattle() {
        return null;
    }

    public BattleDto connectToBattle() {
        return null;
    }

    public List<BattleDto> getOpenBattles() {
        return null;
    }

    public BattleDto makeMove(BattleDto battleDto, StepDto stepDto) {
        //get value from authorized user
        fieldService.move(battleDto.getFieldDto(), stepDto, null);

        return null;
    }
}

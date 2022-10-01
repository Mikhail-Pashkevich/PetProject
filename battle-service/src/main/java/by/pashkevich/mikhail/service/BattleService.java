package by.pashkevich.mikhail.service;

import by.pashkevich.mikhail.model.dto.BattleDto;
import by.pashkevich.mikhail.repository.BattleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BattleService {
    private final BattleRepository battleRepository;

    private final FieldService fieldService;


    public BattleDto create() {
        return null;
    }

    public BattleDto join() {
        return null;
    }

    public List<BattleDto> getOpenedNow() {
        return null;
    }

    public BattleDto makeMove(BattleDto battleDto, Integer step) {
        //get value from authorized user
        fieldService.move(battleDto.getFieldDto(), step, null);

        return null;
    }
}

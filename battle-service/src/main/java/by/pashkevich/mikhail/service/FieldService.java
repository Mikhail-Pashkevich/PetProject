package by.pashkevich.mikhail.service;

import by.pashkevich.mikhail.model.dto.FieldDto;
import by.pashkevich.mikhail.model.entity.Field;
import by.pashkevich.mikhail.model.entity.enums.Value;
import org.springframework.stereotype.Service;

@Service
public class FieldService {

    public Field move(FieldDto fieldDto, Integer step, Value value) {
        //check is win before a move
        //check is field and step correct
        //check can user make a move
        //make a move
        //check is win after a move
        return null;
    }

    private boolean isWin(Field field, Value value) {
        return false;
    }

    private boolean isCorrectField(Field field) {
        return false;
    }

    private boolean isCorrectStep(Field field, Integer step) {
        return false;
    }
}

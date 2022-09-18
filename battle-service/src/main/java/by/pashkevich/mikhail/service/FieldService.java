package by.pashkevich.mikhail.service;

import by.pashkevich.mikhail.model.Field;
import by.pashkevich.mikhail.model.Step;
import by.pashkevich.mikhail.model.Value;
import org.springframework.stereotype.Service;

@Service
public class FieldService {

    public Field move(Field field, Step step, Value value) {
        //check is win before a move
        //check is field and step correct
        //make move
        //check is win after a move
        return null;
    }

    private boolean isWin(Field field,Value value) {
        return false;
    }

    private boolean isCorrectField(Field field) {
        return false;
    }

    private boolean isCorrectStep(Field field, Step step) {
        return false;
    }
}

package by.pashkevich.mikhail.service;

import by.pashkevich.mikhail.model.entity.Battle;
import by.pashkevich.mikhail.model.entity.Field;
import by.pashkevich.mikhail.model.entity.Step;
import by.pashkevich.mikhail.model.entity.User;
import by.pashkevich.mikhail.model.entity.enums.Value;

import java.util.Arrays;

public class CommonMethods {
    public static Long anyId() {
        return 0L;
    }

    public static Step anyStep() {
        return new Step(0, 0);
    }

    public static Value anyValue() {
        return Value.VALUE_X;
    }

    public static User anyUser() {
        User user = new User();

        user.setId(0L);
        user.setLogin("anyLogin");

        return user;
    }

    public static User anyUser(Long id) {
        User user = new User();

        user.setId(id);
        user.setLogin("anyLogin");

        return user;
    }

    public static Value[][] anyBattleArea() {
        Value[][] battleArea = new Value[3][3];

        for (Value[] row : battleArea) {
            Arrays.fill(row, Value.VALUE_EMPTY);
        }

        return battleArea;
    }

    public static Field anyField() {
        return new Field();
    }

    public static Field anyFieldWithBattleArea() {
        return new Field(anyId(), anyBattleArea());
    }

    public static Battle anyBattleWithPlayersWithIds(Long playerXid, Long playerOid) {
        Battle battle = new Battle();

        User playerX = anyUser(playerXid);
        battle.setPlayerX(playerX);
        User playerO = anyUser(playerOid);
        battle.setPlayerO(playerO);

        return battle;
    }
}

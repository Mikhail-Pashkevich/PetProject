package by.pashkevich.mikhail.service;

import by.pashkevich.mikhail.model.User;
import by.pashkevich.mikhail.model.entity.enums.Value;

public class CommonMethods {
    public static Long anyId() {
        return 0L;
    }

    public static Integer anyStep() {
        return 1;
    }

    public static Value anyValue() {
        return Value.VALUE_X;
    }

    public static User anyUser() {
        User user = new User();

        user.setLogin("anyLogin");
        user.setPassword("anyPassword");

        return user;
    }
}

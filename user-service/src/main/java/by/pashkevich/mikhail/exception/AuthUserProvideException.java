package by.pashkevich.mikhail.exception;

import by.pashkevich.mikhail.model.User;

public class AuthUserProvideException extends RuntimeException {
    public AuthUserProvideException(String methodName, String className) {
        super(String.format("Method '%s' of class '%s' does not contain a parameter of type '%s'",
                methodName,
                className,
                User.class.getName()));
    }
}

package by.pashkevich.mikhail.exception;

public class IncorrectDataException extends RuntimeException {
    public IncorrectDataException(String message, Object... args) {
        super(String.format(message, args));
    }
}

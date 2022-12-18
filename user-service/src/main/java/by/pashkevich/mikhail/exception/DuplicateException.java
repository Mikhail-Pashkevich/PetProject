package by.pashkevich.mikhail.exception;

public class DuplicateException extends RuntimeException {
    public DuplicateException(String message, Object... args) {
        super(String.format(message, args));
    }
}

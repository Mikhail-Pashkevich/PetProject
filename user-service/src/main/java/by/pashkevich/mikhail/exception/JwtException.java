package by.pashkevich.mikhail.exception;

public class JwtException extends RuntimeException {
    public JwtException(String message) {
        super("JWT invalid: " + message);
    }
}

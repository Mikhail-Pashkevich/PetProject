package by.pashkevich.mikhail.exception;

public class BattleUnavailableException extends RuntimeException {
    public BattleUnavailableException(Long id) {
        super(String.format("Battle with id = %d unavailable", id));
    }
}

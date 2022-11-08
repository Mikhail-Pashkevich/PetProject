package by.pashkevich.mikhail.service.util.datetime;

public class DatetimeUtil {
    public static long toNanos(int milliseconds) {
        return milliseconds * 1_000_000L;
    }
}

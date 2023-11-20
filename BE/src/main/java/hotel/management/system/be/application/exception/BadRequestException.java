package hotel.management.system.be.application.exception;

/**
 * Wyjątek dla złego żądania.
 */
public class BadRequestException extends RuntimeException {

    /**
     * Tworzy nową instancję wyjątku BadRequestException.
     *
     * @param msg  wiadomość
     * @param args argumenty dla wiadomości
     */
    public BadRequestException(String msg, Object... args) {
        super(String.format(msg, args));
    }
}

package avlyakulov.timur.custom_exception;

public class CurrencyAlreadyExistsException extends RuntimeException {
    public CurrencyAlreadyExistsException() {
        super();
    }

    public CurrencyAlreadyExistsException(String message) {
        super(message);
    }

    public CurrencyAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public CurrencyAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
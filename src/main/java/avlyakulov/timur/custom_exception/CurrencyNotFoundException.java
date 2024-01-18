package avlyakulov.timur.custom_exception;

public class CurrencyNotFoundException extends Exception {
    public CurrencyNotFoundException() {
        super();
    }

    public CurrencyNotFoundException(String message) {
        super(message);
    }

    public CurrencyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

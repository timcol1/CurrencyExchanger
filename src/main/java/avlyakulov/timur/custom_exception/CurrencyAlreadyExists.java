package avlyakulov.timur.custom_exception;

public class CurrencyAlreadyExists extends Exception {
    public CurrencyAlreadyExists() {
        super();
    }

    public CurrencyAlreadyExists(String message) {
        super(message);
    }

    public CurrencyAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }
}
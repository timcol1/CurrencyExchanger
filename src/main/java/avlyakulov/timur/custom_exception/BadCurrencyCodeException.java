package avlyakulov.timur.custom_exception;

public class BadCurrencyCodeException extends RuntimeException {
    public BadCurrencyCodeException() {
        super();
    }

    public BadCurrencyCodeException(String message) {
        super(message);
    }

    public BadCurrencyCodeException(Throwable cause) {
        super(cause);
    }

    public BadCurrencyCodeException(String message, Throwable cause) {
        super(message, cause);
    }
}

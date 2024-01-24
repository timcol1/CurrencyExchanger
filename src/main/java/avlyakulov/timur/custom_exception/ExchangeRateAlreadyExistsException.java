package avlyakulov.timur.custom_exception;

public class ExchangeRateAlreadyExistsException extends RuntimeException {
    public ExchangeRateAlreadyExistsException() {
    }

    public ExchangeRateAlreadyExistsException(String message) {
        super(message);
    }
}
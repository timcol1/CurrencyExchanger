package avlyakulov.timur.custom_exception;

public class ExchangeRateCurrencyPairNotFoundException extends RuntimeException {
    public ExchangeRateCurrencyPairNotFoundException() {
    }

    public ExchangeRateCurrencyPairNotFoundException(String message) {
        super(message);
    }

    public ExchangeRateCurrencyPairNotFoundException(Throwable cause) {
        super(cause);
    }
}
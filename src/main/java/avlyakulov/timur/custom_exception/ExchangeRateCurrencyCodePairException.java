package avlyakulov.timur.custom_exception;

public class ExchangeRateCurrencyCodePairException extends RuntimeException {
    public ExchangeRateCurrencyCodePairException() {

    }

    public ExchangeRateCurrencyCodePairException(String message) {
        super(message);
    }

    public ExchangeRateCurrencyCodePairException(Throwable cause) {
        super(cause);
    }
}

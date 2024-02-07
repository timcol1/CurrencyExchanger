package avlyakulov.timur.custom_exception;

public class RequiredFormFieldIsMissingException extends RuntimeException {
    public RequiredFormFieldIsMissingException() {
    }

    public RequiredFormFieldIsMissingException(String message) {
        super(message);
    }

    public RequiredFormFieldIsMissingException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequiredFormFieldIsMissingException(Throwable cause) {
        super(cause);
    }
}

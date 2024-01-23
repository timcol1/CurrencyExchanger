package avlyakulov.timur.custom_exception;

public class RequiredFormFieldIsMissing extends RuntimeException {
    public RequiredFormFieldIsMissing() {
    }

    public RequiredFormFieldIsMissing(String message) {
        super(message);
    }

    public RequiredFormFieldIsMissing(String message, Throwable cause) {
        super(message, cause);
    }
}

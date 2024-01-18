package avlyakulov.timur.custom_exception;

public class RequiredFormFieldIsMissing extends Exception {
    public RequiredFormFieldIsMissing() {
    }

    public RequiredFormFieldIsMissing(String message) {
        super(message);
    }

    public RequiredFormFieldIsMissing(String message, Throwable cause) {
        super(message, cause);
    }
}

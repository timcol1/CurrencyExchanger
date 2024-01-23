package avlyakulov.timur.custom_exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    String message;

    public ErrorResponse(String message) {
        this.message = message;
    }
}

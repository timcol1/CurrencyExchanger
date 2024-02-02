package avlyakulov.timur.utils;

public class CheckValidityOfParameter {

    public static boolean checkValidityOfParameters(String... parameters) {
        for (String parameter : parameters) {
            if (parameter == null || parameter.isBlank()) {
                return false;
            }
        }
        return true;
    }
}

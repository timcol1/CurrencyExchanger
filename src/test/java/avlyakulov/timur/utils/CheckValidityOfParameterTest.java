package avlyakulov.timur.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CheckValidityOfParameterTest {

    //MethodName_ExpectedBehavior_StateUnderTest
    @Test
    void checkValidityOfParameters_expectsTrue_parametersValid() {
        String code = "USD";
        String sign = "$";

        Assertions.assertTrue(CheckValidityOfParameter.checkValidityOfParameters(code, sign));
    }

    @Test
    void checkValidityOfParameters_expectsFalse_oneParameterEmpty() {
        String code = "USD";
        String sign = " ";

        Assertions.assertFalse(CheckValidityOfParameter.checkValidityOfParameters(code, sign));
    }
}
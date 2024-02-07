package avlyakulov.timur.service;

import avlyakulov.timur.custom_exception.ExchangeRateCurrencyCodePairException;
import avlyakulov.timur.custom_exception.ExchangeRateCurrencyPairNotFoundException;
import avlyakulov.timur.dao.ExchangeRateDao;
import avlyakulov.timur.model.Currency;
import avlyakulov.timur.model.ExchangeRate;
import avlyakulov.timur.service.impl.ExchangeRateServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ExchangeRateServiceTest {

    @Mock
    ExchangeRateDao exchangeRateDao;

    @InjectMocks
    private ExchangeRateServiceImpl exchangeRateService;

    //MethodName_ExpectedBehavior_StateUnderTest
    @Test
    void updateExchangeRate_expectedException_emptyCodePair() {
        String currencyPairCode = "      "; //6 spaces like currency pair
        BigDecimal updateRate = BigDecimal.TEN;

        assertThrows(ExchangeRateCurrencyCodePairException.class, () -> exchangeRateService.updateExchangeRate(currencyPairCode, updateRate));
        Mockito.verify(exchangeRateDao, Mockito.times(0)).findByCodes(any(), any());
        Mockito.verify(exchangeRateDao, Mockito.times(0)).update(any(), any(), any());
    }
}
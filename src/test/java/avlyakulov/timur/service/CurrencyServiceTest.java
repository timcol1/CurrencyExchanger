package avlyakulov.timur.service;

import avlyakulov.timur.custom_exception.BadCurrencyCodeException;
import avlyakulov.timur.custom_exception.CurrencyAlreadyExistsException;
import avlyakulov.timur.custom_exception.CurrencyNotFoundException;
import avlyakulov.timur.dao.CurrencyDao;
import avlyakulov.timur.model.Currency;
import avlyakulov.timur.service.impl.CurrencyServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {

    @Mock
    private CurrencyDao currencyDao;

    @InjectMocks
    private CurrencyServiceImpl currencyService;


    //MethodName_ExpectedBehavior_StateUnderTest
    @Test
    void findByCode_findCurrencyByCode_CurrencyExists() {
        String code = "USD";
        Currency expectedCurrency = new Currency(Long.valueOf("1"), code, "US Dollar", "$");

        Mockito.when(currencyDao.findCurrencyByCode(code)).thenReturn(expectedCurrency);

        Currency currency = currencyService.findByCode(code);

        assertNotNull(currency);
        assertEquals(expectedCurrency, currency);
        Mockito.verify(currencyDao, Mockito.times(1)).findCurrencyByCode(any());
    }


    @Test
    void findByCode_throwCurrencyNotFoundException_CurrencyNotExist() {
        String code = "NOT";

        Mockito.when(currencyDao.findCurrencyByCode(code)).thenThrow(CurrencyNotFoundException.class);

        Assertions.assertThrows(CurrencyNotFoundException.class, () -> currencyService.findByCode(code));
        Mockito.verify(currencyDao, Mockito.times(1)).findCurrencyByCode(any());
    }

    @Test
    void findByCode_throwBadCurrencyCodeException_WrongCode() {
        String code = "INVALID_CODE";

        Assertions.assertThrows(BadCurrencyCodeException.class, () -> currencyService.findByCode(code));
        Mockito.verify(currencyDao, Mockito.times(0)).findCurrencyByCode(any());
    }


    @Test
    void createCurrency_successfullyCreatedCurrency_CreatedCurrencyAndCurrencyWithSuchCodeNotExist() {
        Currency expectedCurrency = new Currency("AUH", "Australian Hrivnya", "A₴");

        Mockito.when(currencyDao.create(expectedCurrency)).thenReturn(expectedCurrency);

        Currency currency = currencyService.createCurrency(expectedCurrency);

        assertNotNull(currency);
        assertEquals(expectedCurrency.getCode(), currency.getCode());
        assertEquals(expectedCurrency.getName(), currency.getName());
        assertEquals(expectedCurrency.getSign(), currency.getSign());
        Mockito.verify(currencyDao, Mockito.times(1)).create(any());
    }
}
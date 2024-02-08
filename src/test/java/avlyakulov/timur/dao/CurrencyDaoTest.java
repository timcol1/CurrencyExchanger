package avlyakulov.timur.dao;

import avlyakulov.timur.custom_exception.CurrencyAlreadyExistsException;
import avlyakulov.timur.custom_exception.CurrencyNotFoundException;
import avlyakulov.timur.model.Currency;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

@Slf4j
class CurrencyDaoTest {

    private CurrencyDao currencyDao = new CurrencyDaoImpl(DeploymentEnvironment.TEST);

    CurrencyDaoTest() throws SQLException {
    }

    @BeforeEach
    void setUpEach() throws URISyntaxException, IOException, SQLException {
        DBState.createDB();
    }

    @AfterEach()
    void cleanUp() throws SQLException {
        DBState.cleanDB();
    }

    //MethodName_ExpectedBehavior_StateUnderTest
    @Test
    void findAll_findCurrencies_find3Currency() {
        List<Currency> currencies = currencyDao.findAll();

        Assertions.assertEquals(3, currencies.size());
    }

    @Test
    void findCurrencyByCode_findCurrency_currencyExists() {
        String code = "USD";
        Currency currency = currencyDao.findCurrencyByCode(code);

        Assertions.assertNotNull(currency);
        Assertions.assertEquals(2, currency.getId());
        Assertions.assertEquals("USD", currency.getCode());
        Assertions.assertEquals("US Dollar", currency.getName());
        Assertions.assertEquals("$", currency.getSign());
    }

    @Test
    void findCurrencyByCode_findCurrency_currencyNotExist() {
        String code = "PLN";

        Assertions.assertThrows(CurrencyNotFoundException.class, () -> currencyDao.findCurrencyByCode(code));
    }

    @Test
    void create_createCurrency_currencyNotExistInDB() throws SQLException {
        Currency expectedCurrency = new Currency("PLN", "Zloty", "Zł");
        Currency currency = currencyDao.create(expectedCurrency);


        Assertions.assertEquals(4, currencyDao.findAll().size());
        Assertions.assertNotNull(currency);
        Assertions.assertEquals(5, currency.getId());
        Assertions.assertEquals(expectedCurrency.getCode(), currency.getCode());
        Assertions.assertEquals(expectedCurrency.getName(), currency.getName());
        Assertions.assertEquals(expectedCurrency.getSign(), currency.getSign());
    }

    @Test
    void create_createCurrency_currencyWithSuchCodeExistInDB() {
        Currency expectedCurrency = new Currency("USD", "US Dollar", "$");

        Assertions.assertThrowsExactly(CurrencyAlreadyExistsException.class, () -> currencyDao.create(expectedCurrency));
    }
}
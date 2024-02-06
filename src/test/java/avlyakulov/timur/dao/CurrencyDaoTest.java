package avlyakulov.timur.dao;

import avlyakulov.timur.connection.DataSourceSimpleConnectionTestDB;
import avlyakulov.timur.model.Currency;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.List;
import java.util.Optional;

@Slf4j
class CurrencyDaoTest {

    private CurrencyDao currencyDao = new CurrencyDaoImpl(DataSourceSimpleConnectionTestDB.getConnection());

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
        Optional<Currency> currency = currencyDao.findCurrencyByCode(code);

        Assertions.assertTrue(currency.isPresent());
        Assertions.assertEquals(2, currency.get().getId());
        Assertions.assertEquals("USD", currency.get().getCode());
        Assertions.assertEquals("US Dollar", currency.get().getFullName());
        Assertions.assertEquals("$", currency.get().getSign());
    }

    @Test
    void findCurrencyByCode_findCurrency_currencyNotExist() {
        String code = "PLN";
        Optional<Currency> currency = currencyDao.findCurrencyByCode(code);

        Assertions.assertFalse(currency.isPresent());
    }

    @Test
    void create_createCurrency_currencyNotExistInDB() throws SQLException {
        Currency expectedCurrency = new Currency("PLN", "Zloty", "Zł");
        Currency currency = currencyDao.create(expectedCurrency);
        //это нужно чтоб еще раз открыть соединение
        currencyDao = new CurrencyDaoImpl(DataSourceSimpleConnectionTestDB.getConnection());
        int sizeCurrencies = currencyDao.findAll().size();

        Assertions.assertEquals(4, sizeCurrencies);
        Assertions.assertNotNull(currency);
        Assertions.assertEquals(5, currency.getId());
        Assertions.assertEquals(expectedCurrency.getCode(), currency.getCode());
        Assertions.assertEquals(expectedCurrency.getFullName(), currency.getFullName());
        Assertions.assertEquals(expectedCurrency.getSign(), currency.getSign());
    }

    @Test
    void create_createCurrency_currencyWithSuchCodeExistInDB() {
        Currency expectedCurrency = new Currency("USD", "US Dollar", "$");

        Assertions.assertThrows(RuntimeException.class, () -> currencyDao.create(expectedCurrency));
    }
}
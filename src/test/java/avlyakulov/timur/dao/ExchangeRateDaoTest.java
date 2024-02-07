package avlyakulov.timur.dao;

import avlyakulov.timur.custom_exception.ExchangeRateCurrencyPairNotFoundException;
import avlyakulov.timur.model.Currency;
import avlyakulov.timur.model.ExchangeRate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExchangeRateDaoTest {
    private ExchangeRateDao exchangeRateDao = new ExchangeRateDaoImpl(DeploymentEnvironment.TEST);

    ExchangeRateDaoTest() throws SQLException {
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
    void findAll_findAllCurrencyPair_pairsExist() {
        List<ExchangeRate> exchangeRates = exchangeRateDao.findAll();

        assertNotNull(exchangeRates);
        assertEquals(2, exchangeRates.size());
    }

    @Test
    void findByCodes_findCurrencyPair_pairExists() {
        String baseCurrencyCode = "USD";
        String targetCurrencyCode = "UAH";
        ExchangeRate exchangeRate = exchangeRateDao.findByCodes(baseCurrencyCode, targetCurrencyCode);

        assertNotNull(exchangeRate);
        assertEquals(1, exchangeRate.getId());
        assertEquals(new BigDecimal("37.65"), exchangeRate.getRate());
    }

    @Test
    void findByCodes_findCurrencyPair_pairNotExist() {
        String baseCurrencyCode = "USD";
        String targetCurrencyCode = "PLN";


        assertThrows(ExchangeRateCurrencyPairNotFoundException.class, () -> exchangeRateDao.findByCodes(baseCurrencyCode, targetCurrencyCode));
    }

    @Test
    void create_createCurrencyPair_currencyPairNotExist() throws SQLException {
        Currency baseCurrency = new Currency("EUR");
        Currency targetCurrency = new Currency("UAH");
        BigDecimal rate = new BigDecimal("40.48");
        ExchangeRate exchangeRateExpected = new ExchangeRate(baseCurrency, targetCurrency, rate);

        ExchangeRate exchangeRate = exchangeRateDao.create(exchangeRateExpected);

        //exchangeRateDao = new ExchangeRateDaoImpl(DataSourceSimpleConnectionTestDB.getConnection());
        List<ExchangeRate> exchangeRates = exchangeRateDao.findAll();

        assertNotNull(exchangeRate);
        assertEquals(rate, exchangeRate.getRate());
        assertEquals(3, exchangeRates.size());
    }

    @Test
    void create_throwException_currencyPairExists() {
        Currency baseCurrency = new Currency("USD");
        Currency targetCurrency = new Currency("UAH");
        BigDecimal rate = new BigDecimal("37.65");
        ExchangeRate exchangeRateExpected = new ExchangeRate(baseCurrency, targetCurrency, rate);

        assertThrows(RuntimeException.class, () -> exchangeRateDao.create(exchangeRateExpected));
    }

    @Test
    void update_updateCurrencyPair_currencyPairExists() {
        String baseCurrencyCode = "USD";
        String targetCurrencyCode = "UAH";
        BigDecimal updatedRate = new BigDecimal("27.65");

        ExchangeRate exchangeRate = exchangeRateDao.update(baseCurrencyCode, targetCurrencyCode, updatedRate);
        assertNotNull(exchangeRate);
        assertEquals(updatedRate, exchangeRate.getRate());
    }

    @Test
    void update_updateCurrencyPair_currencyPairNotExist() {
        String baseCurrencyCode = "USD";
        String targetCurrencyCode = "PLN";
        BigDecimal updatedRate = new BigDecimal("4.04");

        assertThrows(ExchangeRateCurrencyPairNotFoundException.class, () -> exchangeRateDao.update(baseCurrencyCode, targetCurrencyCode, updatedRate));
    }

}
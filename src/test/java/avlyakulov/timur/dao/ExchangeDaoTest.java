package avlyakulov.timur.dao;

import avlyakulov.timur.connection.DataSourceSimpleConnectionTestDB;
import avlyakulov.timur.custom_exception.ExchangeRateCurrencyPairNotFoundException;
import avlyakulov.timur.model.Exchange;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ExchangeDaoTest {

    private ExchangeDao exchangeDao = new ExchangeDaoImpl(DataSourceSimpleConnectionTestDB.getConnection());

    ExchangeDaoTest() throws SQLException {
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
    void exchange_exchangeAB_currencyCodesExists() {
        String baseCurrencyCode = "USD";
        String targetCurrencyCode = "UAH";
        BigDecimal amount = BigDecimal.TEN;

        Exchange exchange = exchangeDao.exchange(baseCurrencyCode, targetCurrencyCode, amount);

        Assertions.assertNotNull(exchange);
        Assertions.assertEquals(amount, exchange.getAmount());
        Assertions.assertEquals(new BigDecimal("37.65"), exchange.getRate());
        Assertions.assertEquals(new BigDecimal("376.50"), exchange.getConvertedAmount());
    }

    @Test
    void exchange_exchangeBA_currencyCodesExists() {
        String baseCurrencyCode = "UAH";
        String targetCurrencyCode = "USD";
        BigDecimal amount = BigDecimal.TEN;

        Exchange exchange = exchangeDao.exchange(baseCurrencyCode, targetCurrencyCode, amount);

        Assertions.assertNotNull(exchange);
        Assertions.assertEquals(amount, exchange.getAmount());
        Assertions.assertEquals(new BigDecimal("0.03"), exchange.getRate());
        Assertions.assertEquals(new BigDecimal("0.30"), exchange.getConvertedAmount());
    }

    @Test
    void exchange_exchangeUSDAUSDB_currencyCodesExists() {
        String baseCurrencyCode = "EUR";
        String targetCurrencyCode = "UAH";
        BigDecimal amount = BigDecimal.TEN;

        Exchange exchange = exchangeDao.exchange(baseCurrencyCode, targetCurrencyCode, amount);

        Assertions.assertNotNull(exchange);
        Assertions.assertEquals(amount, exchange.getAmount());
        Assertions.assertEquals(new BigDecimal("40.48"), exchange.getRate());
        Assertions.assertEquals(new BigDecimal("404.80"), exchange.getConvertedAmount());
    }

    @Test
    void exchange_throwException_currencyPairCodesNotExist() {
        String baseCurrencyCode = "USD";
        String targetCurrencyCode = "PLN";
        BigDecimal amount = BigDecimal.TEN;

        Assertions.assertThrows(ExchangeRateCurrencyPairNotFoundException.class, () -> exchangeDao.exchange(baseCurrencyCode, targetCurrencyCode, amount));
    }
}
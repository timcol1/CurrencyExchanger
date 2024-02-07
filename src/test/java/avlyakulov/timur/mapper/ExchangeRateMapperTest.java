package avlyakulov.timur.mapper;

import avlyakulov.timur.dto.exchange.ExchangeRateRequest;
import avlyakulov.timur.dto.exchange.ExchangeRateResponse;
import avlyakulov.timur.model.Currency;
import avlyakulov.timur.model.ExchangeRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ExchangeRateMapperTest {

    ExchangeRateMapper exchangeRateMapper;

    @BeforeEach
    void setUp() {
        exchangeRateMapper = new ExchangeRateMapper();
    }

    @Test
    void mapToEntityTest() {
        String baseCurrencyCode = "USD";
        String targetCurrencyCode = "EUR";
        BigDecimal rate = BigDecimal.ONE;
        ExchangeRateRequest exchangeRateRequest = new ExchangeRateRequest(baseCurrencyCode, targetCurrencyCode, rate);

        ExchangeRate exchangeRate = exchangeRateMapper.mapToEntity(exchangeRateRequest);

        assertNotNull(exchangeRate);
        assertEquals(baseCurrencyCode, exchangeRate.getBaseCurrency().getCode());
        assertEquals(targetCurrencyCode, exchangeRate.getTargetCurrency().getCode());
        assertEquals(rate, exchangeRate.getRate());
    }

    @Test
    void mapToResponseTest() {
        Currency baseCurrency = new Currency(Long.valueOf("1"), "USD", "US Dollar", "$");
        Currency targetCurrency = new Currency(Long.valueOf("2"), "EUR", "Euro", "€");
        BigDecimal rate = BigDecimal.ONE;
        ExchangeRate exchangeRate = new ExchangeRate(Long.valueOf("1"), baseCurrency, targetCurrency, rate);

        ExchangeRateResponse exchangeRateResponse = exchangeRateMapper.mapToResponse(exchangeRate);

        assertNotNull(exchangeRateResponse);
        assertEquals(1, exchangeRateResponse.getId());
        assertEquals(rate, exchangeRateResponse.getRate());
    }
}
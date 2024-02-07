package avlyakulov.timur.mapper;

import avlyakulov.timur.dto.exchange.ExchangeResponse;
import avlyakulov.timur.model.Currency;
import avlyakulov.timur.model.Exchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ExchangeMapperTest {

    ExchangeMapper exchangeMapper;

    @BeforeEach
    void setUp() {
        exchangeMapper = new ExchangeMapper();
    }

    @Test
    public void testMapToEntity() {
        Currency baseCurrency = new Currency(Long.valueOf("1"), "USD", "US Dollar", "$");
        Currency targetCurrency = new Currency(Long.valueOf("2"), "EUR", "Euro", "€");
        BigDecimal rate = BigDecimal.ONE;
        BigDecimal amount = BigDecimal.TEN;
        BigDecimal convertedAmount = BigDecimal.TEN;
        Exchange exchange = new Exchange(baseCurrency, targetCurrency, rate, amount, convertedAmount);

        ExchangeResponse exchangeResponse = exchangeMapper.mapToResponse(exchange);

        assertNotNull(exchangeResponse);
        assertEquals(rate, exchangeResponse.getRate());
        assertEquals(amount, exchangeResponse.getAmount());
        assertEquals(convertedAmount, exchangeResponse.getConvertedAmount());
    }
}
package avlyakulov.timur.mapper;

import avlyakulov.timur.dto.currency.CurrencyRequest;
import avlyakulov.timur.dto.currency.CurrencyResponse;
import avlyakulov.timur.model.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CurrencyMapperTest {


    CurrencyMapper currencyMapper;

    @BeforeEach
    void setUp() {
        currencyMapper = new CurrencyMapper();
    }

    @Test
    public void testMapToEntity() {
        // Arrange
        CurrencyRequest currencyRequest = new CurrencyRequest("USD", "US Dollar", "$");

        // Act
        Currency currency = currencyMapper.mapToEntity(currencyRequest);

        // Assert
        assertNotNull(currency);
        assertEquals(currencyRequest.getCode(), currency.getCode());
        assertEquals(currencyRequest.getFullName(), currency.getFullName());
        assertEquals(currencyRequest.getSign(), currency.getSign());
    }

    @Test
    public void testMapToResponse() {
        // Arrange
        Currency currency = new Currency(1, "USD", "US Dollar", "$");

        // Act
        CurrencyResponse currencyResponse = currencyMapper.mapToResponse(currency);

        // Assert
        assertNotNull(currencyResponse);
        assertEquals(currency.getId(), currencyResponse.getId());
        assertEquals(currency.getCode(), currencyResponse.getCode());
        assertEquals(currency.getFullName(), currencyResponse.getFullName());
        assertEquals(currency.getSign(), currencyResponse.getSign());
    }

}
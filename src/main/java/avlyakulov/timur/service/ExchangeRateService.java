package avlyakulov.timur.service;

import avlyakulov.timur.dto.exchange.ExchangeResponse;
import avlyakulov.timur.model.ExchangeRate;

import java.math.BigDecimal;
import java.util.List;

public interface ExchangeRateService {

    List<ExchangeRate> findAll();

    ExchangeRate findByCodes(String currencyPairCode);

    ExchangeRate createExchangeRate(ExchangeRate exchangeRate);

    ExchangeRate updateExchangeRate(String currencyPairCode, BigDecimal updatedRate);

    ExchangeResponse exchange(String baseCurrencyCode,String targetCurrencyCode, BigDecimal amount);
}

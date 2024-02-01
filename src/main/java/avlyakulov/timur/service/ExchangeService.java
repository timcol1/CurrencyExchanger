package avlyakulov.timur.service;

import avlyakulov.timur.model.Exchange;

import java.math.BigDecimal;

public interface ExchangeService {

    Exchange exchange(String baseCurrencyCode, String targetCurrencyCode, BigDecimal amount);
}
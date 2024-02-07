package avlyakulov.timur.dao;

import avlyakulov.timur.model.ExchangeRate;

import java.math.BigDecimal;
import java.util.Optional;

public interface ExchangeRateDao extends CrudDao<ExchangeRate> {

    ExchangeRate findByCodes(String baseCurrencyCode, String targetCurrencyCode);

    ExchangeRate update(String baseCurrencyCode, String targetCurrencyCode, BigDecimal rate);
}
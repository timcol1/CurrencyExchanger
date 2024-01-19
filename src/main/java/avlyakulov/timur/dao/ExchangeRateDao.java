package avlyakulov.timur.dao;

import avlyakulov.timur.model.ExchangeRate;

public interface ExchangeRateDao extends CrudDao<ExchangeRate, Integer> {
    public ExchangeRate findByCodes(String baseCurrencyCode, String targetCurrencyCode);
}
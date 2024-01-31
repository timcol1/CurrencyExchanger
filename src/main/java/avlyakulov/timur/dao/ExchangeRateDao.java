package avlyakulov.timur.dao;

import avlyakulov.timur.connection.ConnectionBuilder;
import avlyakulov.timur.model.ExchangeRate;

import java.math.BigDecimal;
import java.util.Optional;

public interface ExchangeRateDao extends CrudDao<ExchangeRate, Integer> {

    Optional<ExchangeRate> findByCodes(String baseCurrencyCode, String targetCurrencyCode);

    ExchangeRate update(String baseCurrencyCode, String targetCurrencyCode, BigDecimal rate);

    void setConnectionBuilder(ConnectionBuilder connectionBuilder);
}
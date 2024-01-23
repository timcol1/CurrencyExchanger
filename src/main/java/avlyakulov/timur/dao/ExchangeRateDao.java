package avlyakulov.timur.dao;

import avlyakulov.timur.connection.ConnectionBuilder;
import avlyakulov.timur.model.ExchangeRate;

import java.util.Optional;

public interface ExchangeRateDao extends CrudDao<ExchangeRate, Integer> {

    Optional<ExchangeRate> findByCodes(String baseCurrencyCode, String targetCurrencyCode);

    void setConnectionBuilder(ConnectionBuilder connectionBuilder);
}
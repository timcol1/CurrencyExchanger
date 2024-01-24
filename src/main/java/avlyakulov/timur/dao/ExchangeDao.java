package avlyakulov.timur.dao;

import avlyakulov.timur.connection.ConnectionBuilder;
import avlyakulov.timur.model.Exchange;

import java.math.BigDecimal;

public interface ExchangeDao {

    public Exchange exchange(String baseCurrencyCode, String targetCurrencyCode, BigDecimal amount);

    void setConnectionBuilder(ConnectionBuilder connectionBuilder);

}
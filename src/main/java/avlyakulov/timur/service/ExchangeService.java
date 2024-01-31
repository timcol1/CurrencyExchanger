package avlyakulov.timur.service;

import avlyakulov.timur.connection.ConnectionBuilder;
import avlyakulov.timur.connection.PoolConnectionBuilder;
import avlyakulov.timur.dao.ExchangeDao;
import avlyakulov.timur.dao.ExchangeDaoImpl;
import avlyakulov.timur.model.Exchange;

import java.math.BigDecimal;

public class ExchangeService {

    ExchangeDao exchangeDao = new ExchangeDaoImpl();

    public void setConnectionBuilder(ConnectionBuilder connectionBuilder) {
        exchangeDao.setConnectionBuilder(connectionBuilder);
    }

    public Exchange exchange(String baseCurrencyCode, String targetCurrencyCode, BigDecimal amount) {
        return exchangeDao.exchange(baseCurrencyCode, targetCurrencyCode, amount);
    }
}
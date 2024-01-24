package avlyakulov.timur.service;

import avlyakulov.timur.connection.PoolConnectionBuilder;
import avlyakulov.timur.dao.ExchangeDao;
import avlyakulov.timur.dao.ExchangeDaoImpl;
import avlyakulov.timur.model.Exchange;

import java.math.BigDecimal;

public class ExchangeService {

    ExchangeDao exchangeDao = new ExchangeDaoImpl();

    public ExchangeService() {
        exchangeDao.setConnectionBuilder(new PoolConnectionBuilder());
    }

    public Exchange exchange(String baseCurrencyCode, String targetCurrencyCode, BigDecimal amount) {
        return exchangeDao.exchange(baseCurrencyCode, targetCurrencyCode, amount);
    }
}
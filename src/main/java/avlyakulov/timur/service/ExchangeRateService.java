package avlyakulov.timur.service;

import avlyakulov.timur.connection.PoolConnectionBuilder;
import avlyakulov.timur.dao.ExchangeRateDao;
import avlyakulov.timur.dao.ExchangeRateDaoImpl;
import avlyakulov.timur.model.ExchangeRate;

import java.util.List;

public class ExchangeRateService {

    ExchangeRateDao exchangeRateDao = new ExchangeRateDaoImpl();

    public ExchangeRateService() {
        exchangeRateDao.setConnectionBuilder(new PoolConnectionBuilder());
    }

    public List<ExchangeRate> findAll() {
        return exchangeRateDao.findAll();
    }
}

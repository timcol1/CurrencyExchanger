package avlyakulov.timur.service.impl;

import avlyakulov.timur.dao.ExchangeDao;
import avlyakulov.timur.model.Exchange;
import avlyakulov.timur.service.ExchangeService;

import java.math.BigDecimal;

public class ExchangeServiceImpl implements ExchangeService {

    private final ExchangeDao exchangeDao;

    public ExchangeServiceImpl(ExchangeDao exchangeDao) {
        this.exchangeDao = exchangeDao;
    }


    public Exchange exchange(String baseCurrencyCode, String targetCurrencyCode, BigDecimal amount) {
        return exchangeDao.exchange(baseCurrencyCode, targetCurrencyCode, amount);
    }
}
package avlyakulov.timur.service.impl;

import avlyakulov.timur.custom_exception.ExchangeRateCurrencyCodePairException;
import avlyakulov.timur.custom_exception.ExchangeRateCurrencyPairNotFoundException;
import avlyakulov.timur.dao.ExchangeRateDao;
import avlyakulov.timur.model.ExchangeRate;
import avlyakulov.timur.service.ExchangeRateService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


public class ExchangeRateServiceImpl implements ExchangeRateService {


    private final ExchangeRateDao exchangeRateDao;

    public ExchangeRateServiceImpl(ExchangeRateDao exchangeRateDao) {
        this.exchangeRateDao = exchangeRateDao;
    }


    public List<ExchangeRate> findAll() {
        return exchangeRateDao.findAll();
    }

    public ExchangeRate findByCodes(String currencyPairCode) {
        String baseCurrencyCode = currencyPairCode.substring(0, 3);
        String targetCurrencyCode = currencyPairCode.substring(3);

        return exchangeRateDao.findByCodes(baseCurrencyCode, targetCurrencyCode);
    }

    public ExchangeRate createExchangeRate(ExchangeRate exchangeRate) {
        return exchangeRateDao.create(exchangeRate);
    }


    public ExchangeRate updateExchangeRate(String currencyPairCode, BigDecimal updatedRate) {
        String baseCurrencyCode = currencyPairCode.substring(0, 3);
        String targetCurrencyCode = currencyPairCode.substring(3);

        return exchangeRateDao.update(baseCurrencyCode, targetCurrencyCode, updatedRate);
    }
}
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
    private final int CURRENCY_PAIR_CODE_LENGTH_URL = 6;

    private final ExchangeRateDao exchangeRateDao;

    public ExchangeRateServiceImpl(ExchangeRateDao exchangeRateDao) {
        this.exchangeRateDao = exchangeRateDao;
    }


    public List<ExchangeRate> findAll() {
        return exchangeRateDao.findAll();
    }

    public ExchangeRate findByCodes(String currencyPairCode) {
        if (currencyPairCode.isBlank() || currencyPairCode.length() != CURRENCY_PAIR_CODE_LENGTH_URL) {
            throw new ExchangeRateCurrencyCodePairException("The currency codes of the pair are missing from the address or it is specified incorrectly");
        } else {
            String baseCurrencyCode = currencyPairCode.substring(0, 3);
            String targetCurrencyCode = currencyPairCode.substring(3);

            return exchangeRateDao.findByCodes(baseCurrencyCode, targetCurrencyCode);
        }
    }

    public ExchangeRate createExchangeRate(ExchangeRate exchangeRate) {
        return exchangeRateDao.create(exchangeRate);
    }


    public ExchangeRate updateExchangeRate(String currencyPairCode, BigDecimal updatedRate) {
        if (currencyPairCode.isBlank() || currencyPairCode.length() != CURRENCY_PAIR_CODE_LENGTH_URL) {
            throw new ExchangeRateCurrencyCodePairException("The currency codes of the pair are missing from the address or it is specified incorrectly");
        } else {
            String baseCurrencyCode = currencyPairCode.substring(0, 3);
            String targetCurrencyCode = currencyPairCode.substring(3);

            return exchangeRateDao.update(baseCurrencyCode, targetCurrencyCode, updatedRate);
        }
    }
}
package avlyakulov.timur.service;

import avlyakulov.timur.connection.PoolConnectionBuilder;
import avlyakulov.timur.custom_exception.ExchangeRateCurrencyCodePairException;
import avlyakulov.timur.custom_exception.ExchangeRateCurrencyPairNotFoundException;
import avlyakulov.timur.dao.ExchangeRateDao;
import avlyakulov.timur.dao.ExchangeRateDaoImpl;
import avlyakulov.timur.model.ExchangeRate;

import java.util.List;
import java.util.Optional;

public class ExchangeRateService {
    private final int CURRENCY_PAIR_CODE_LENGTH_URL = 6;

    ExchangeRateDao exchangeRateDao = new ExchangeRateDaoImpl();

    public ExchangeRateService() {
        exchangeRateDao.setConnectionBuilder(new PoolConnectionBuilder());
    }

    public List<ExchangeRate> findAll() {
        return exchangeRateDao.findAll();
    }

    public ExchangeRate findByCodes(String currencyPairCode) {
        if (currencyPairCode.length() != CURRENCY_PAIR_CODE_LENGTH_URL) {
            throw new ExchangeRateCurrencyCodePairException("The currency codes of the pair are missing from the address or it is specified incorrectly");
        } else {
            String baseCurrencyCode = currencyPairCode.substring(0, 3);
            String targetCurrencyCode = currencyPairCode.substring(3);

            Optional<ExchangeRate> exchangeRate = exchangeRateDao.findByCodes(baseCurrencyCode, targetCurrencyCode);
            if (exchangeRate.isPresent()) {
                return exchangeRate.get();
            } else {
                throw new ExchangeRateCurrencyPairNotFoundException("The exchange rate with such code pair " + currencyPairCode + " doesn't exist");
            }
        }
    }

}
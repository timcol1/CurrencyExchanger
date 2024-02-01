package avlyakulov.timur.service;

import avlyakulov.timur.connection.ConnectionBuilder;
import avlyakulov.timur.custom_exception.CurrencyNotFoundException;
import avlyakulov.timur.custom_exception.ExchangeRateAlreadyExistsException;
import avlyakulov.timur.custom_exception.ExchangeRateCurrencyCodePairException;
import avlyakulov.timur.custom_exception.ExchangeRateCurrencyPairNotFoundException;
import avlyakulov.timur.dao.CurrencyDao;
import avlyakulov.timur.dao.CurrencyDaoImpl;
import avlyakulov.timur.dao.ExchangeRateDao;
import avlyakulov.timur.dao.ExchangeRateDaoImpl;
import avlyakulov.timur.model.Currency;
import avlyakulov.timur.model.ExchangeRate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


public class ExchangeRateService {
    private final int CURRENCY_PAIR_CODE_LENGTH_URL = 6;

    ExchangeRateDao exchangeRateDao = new ExchangeRateDaoImpl();

    CurrencyDao currencyDao = new CurrencyDaoImpl();

    public void setConnectionBuilder(ConnectionBuilder connectionBuilder) {
        exchangeRateDao.setConnectionBuilder(connectionBuilder);
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

    public ExchangeRate createExchangeRate(ExchangeRate exchangeRate) {

        String baseCurrencyCode = exchangeRate.getBaseCurrency().getCode();
        String targetCurrencyCode = exchangeRate.getTargetCurrency().getCode();

        if (checkExistenceOfCurrencyPair(exchangeRateDao.findByCodes(baseCurrencyCode, targetCurrencyCode))) {
            Optional<Currency> baseCurrency = currencyDao.findCurrencyByCode(baseCurrencyCode);
            Optional<Currency> targetCurrency = currencyDao.findCurrencyByCode(targetCurrencyCode);

            if (baseCurrency.isPresent() && targetCurrency.isPresent()) {
                return exchangeRateDao.create(exchangeRate);
            } else {
                throw new CurrencyNotFoundException("One (or both) currencies from the currency pair does not exist in the database");
            }
        } else {
            throw new ExchangeRateAlreadyExistsException("A currency pair with this code pair already exists ");
        }
    }


    private boolean checkExistenceOfCurrencyPair(Optional<ExchangeRate> exchangeRate) {
        return exchangeRate.isEmpty();
    }

    public ExchangeRate updateExchangeRate(String currencyPairCode, BigDecimal updatedRate) {
        if (currencyPairCode.length() != CURRENCY_PAIR_CODE_LENGTH_URL) {
            throw new ExchangeRateCurrencyCodePairException("The currency codes of the pair are missing from the address or it is specified incorrectly");
        } else {
            String baseCurrencyCode = currencyPairCode.substring(0, 3);
            String targetCurrencyCode = currencyPairCode.substring(3);

            Optional<ExchangeRate> exchangeRate = exchangeRateDao.findByCodes(baseCurrencyCode, targetCurrencyCode);
            if (exchangeRate.isPresent()) {
                return exchangeRateDao.update(baseCurrencyCode, targetCurrencyCode, updatedRate);
            } else {
                throw new ExchangeRateCurrencyPairNotFoundException("The exchange rate with such code pair " + currencyPairCode + " doesn't exist");
            }
        }
    }

}
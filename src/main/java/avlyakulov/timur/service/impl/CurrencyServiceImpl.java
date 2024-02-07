package avlyakulov.timur.service.impl;

import avlyakulov.timur.custom_exception.BadCurrencyCodeException;
import avlyakulov.timur.custom_exception.CurrencyAlreadyExistsException;
import avlyakulov.timur.custom_exception.CurrencyNotFoundException;
import avlyakulov.timur.dao.CurrencyDao;
import avlyakulov.timur.model.Currency;
import avlyakulov.timur.service.CurrencyService;

import java.util.List;
import java.util.Optional;

public class CurrencyServiceImpl implements CurrencyService {
    private static final int CODE_LENGTH_URL = 3;

    private final CurrencyDao currencyDao;

    public CurrencyServiceImpl(CurrencyDao currencyDao) {
        this.currencyDao = currencyDao;
    }


    public List<Currency> findAll() {
        return currencyDao.findAll();
    }

    public Currency findByCode(String code) {
        if (code.isBlank() || code.length() != CODE_LENGTH_URL) {
            throw new BadCurrencyCodeException("Currency code is missing at address or you put wrong code");
        } else {
            return currencyDao.findCurrencyByCode(code);
        }
    }

    public Currency createCurrency(Currency currency) {
        return currencyDao.create(currency);
    }
}
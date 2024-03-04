package avlyakulov.timur.service.impl;

import avlyakulov.timur.dao.CurrencyDao;
import avlyakulov.timur.model.Currency;
import avlyakulov.timur.service.CurrencyService;

import java.util.List;

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
        return currencyDao.findCurrencyByCode(code);
    }

    public Currency createCurrency(Currency currency) {
        return currencyDao.create(currency);
    }
}
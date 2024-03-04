package avlyakulov.timur.service.impl;

import avlyakulov.timur.connection.DeploymentEnvironment;
import avlyakulov.timur.dao.CurrencyDao;
import avlyakulov.timur.dao.CurrencyDaoImpl;
import avlyakulov.timur.model.Currency;
import avlyakulov.timur.service.CurrencyService;

import java.util.List;

public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyDao currencyDao = new CurrencyDaoImpl(DeploymentEnvironment.PROD);


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
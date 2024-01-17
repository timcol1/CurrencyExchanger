package avlyakulov.timur.service;

import avlyakulov.timur.dao.CurrencyDao;
import avlyakulov.timur.dao.PoolConnectionBuilder;
import avlyakulov.timur.model.Currency;

import java.util.List;
import java.util.Optional;

public class CurrencyService {

    CurrencyDao currencyDao = new CurrencyDao();

    public CurrencyService() {
        currencyDao.setConnectionBuilder(new PoolConnectionBuilder());
    }

    public List<Currency> findAll() {
        Optional<List<Currency>> currencyList = currencyDao.findAll();
        if (currencyList.isEmpty()) {
            throw new RuntimeException("Something went wrong");
        } else {
            return currencyList.get();
        }
    }
}

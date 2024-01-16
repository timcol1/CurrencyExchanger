package avlyakulov.timur.service;

import avlyakulov.timur.dao.CurrencyDao;
import avlyakulov.timur.model.Currency;

import java.util.List;
import java.util.Optional;

public class CurrencyService {

    CurrencyDao currencyDao = new CurrencyDao();

    public List<Currency> findAll() {
        Optional<List<Currency>> currencyList = currencyDao.findAll();
        if (currencyList.isEmpty()) {
            throw new RuntimeException("No currencies in db ");
        } else {
            return currencyList.get();
        }
    }
}

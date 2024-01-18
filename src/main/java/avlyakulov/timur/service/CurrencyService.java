package avlyakulov.timur.service;

import avlyakulov.timur.dao.CurrencyDaoImpl;
import avlyakulov.timur.connection.PoolConnectionBuilder;
import avlyakulov.timur.model.Currency;

import java.util.List;
import java.util.Optional;

public class CurrencyService {

    CurrencyDaoImpl currencyDaoImpl = new CurrencyDaoImpl();

    public CurrencyService() {
        currencyDaoImpl.setConnectionBuilder(new PoolConnectionBuilder());
    }

    public List<Currency> findAll() {
        Optional<List<Currency>> currencyList = currencyDaoImpl.findAll();
        if (currencyList.isEmpty()) {
            throw new RuntimeException("Something went wrong");
        } else {
            return currencyList.get();
        }
    }
}

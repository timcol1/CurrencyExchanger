package avlyakulov.timur.service;

import avlyakulov.timur.custom_exception.BadCurrencyCodeException;
import avlyakulov.timur.custom_exception.CurrencyNotFoundException;
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
        return currencyDaoImpl.findAll();
    }

    public Currency findByCode(String code) throws CurrencyNotFoundException, BadCurrencyCodeException {
        if (code.length() != 4) {
            throw new BadCurrencyCodeException("Currency code is missing at address or you put wrong code");
        } else {
            code = code.substring(1);
            Optional<Currency> currency = currencyDaoImpl.findCurrencyByCode(code);
            if (currency.isEmpty()) {
                throw new CurrencyNotFoundException("Currency with this code wasn't found");
            } else {
                return currency.get();
            }
        }
    }
}
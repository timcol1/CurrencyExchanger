package avlyakulov.timur.service;

import avlyakulov.timur.custom_exception.BadCurrencyCodeException;
import avlyakulov.timur.custom_exception.CurrencyAlreadyExists;
import avlyakulov.timur.custom_exception.CurrencyNotFoundException;
import avlyakulov.timur.custom_exception.RequiredFormFieldIsMissing;
import avlyakulov.timur.dao.CurrencyDao;
import avlyakulov.timur.dao.CurrencyDaoImpl;
import avlyakulov.timur.connection.PoolConnectionBuilder;
import avlyakulov.timur.model.Currency;

import java.util.List;
import java.util.Optional;

public class CurrencyService {

    CurrencyDao currencyDao = new CurrencyDaoImpl();

    public CurrencyService() {
        currencyDao.setConnectionBuilder(new PoolConnectionBuilder());
    }

    public List<Currency> findAll() {
        return currencyDao.findAll();
    }

    public Currency findByCode(String code) throws CurrencyNotFoundException, BadCurrencyCodeException {
        if (code.length() != 4) {
            throw new BadCurrencyCodeException("Currency code is missing at address or you put wrong code");
        } else {
            code = code.substring(1);
            Optional<Currency> currency = currencyDao.findCurrencyByCode(code);
            if (currency.isEmpty()) {
                throw new CurrencyNotFoundException("Currency with this code " + code + " wasn't found");
            } else {
                return currency.get();
            }
        }
    }

    public Currency createCurrency(Currency currency) throws RequiredFormFieldIsMissing, CurrencyAlreadyExists {
        if (checkValidityOfParameters(currency.getCode(), currency.getFullName(), currency.getSign())) {
            Optional<Currency> currencyOptional = findSimpleByCode(currency.getCode());
            if (currencyOptional.isPresent()) {
                throw new CurrencyAlreadyExists("Currency with such code " + currency.getCode() + " is already exists");
            } else {
                return currencyDao.create(currency);
            }
        } else {
            throw new RequiredFormFieldIsMissing("A required field in currency is missing");
        }
    }

    private Optional<Currency> findSimpleByCode(String code) {
        return currencyDao.findCurrencyByCode(code);
    }

    private boolean checkValidityOfParameters(String... parameters) {
        for (String parameter : parameters) {
            if (parameter == null || parameter.isBlank()) {
                return false;
            }
        }
        return true;
    }
}
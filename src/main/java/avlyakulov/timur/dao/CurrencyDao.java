package avlyakulov.timur.dao;

import avlyakulov.timur.model.Currency;

public interface CurrencyDao extends CrudDao<Currency> {

    Currency findCurrencyByCode(String code);
}
package avlyakulov.timur.dao;

import avlyakulov.timur.connection.ConnectionBuilder;
import avlyakulov.timur.model.Currency;

import java.util.Optional;

public interface CurrencyDao extends CrudDao<Currency, Integer> {

    Optional<Currency> findCurrencyByCode(String code);
}
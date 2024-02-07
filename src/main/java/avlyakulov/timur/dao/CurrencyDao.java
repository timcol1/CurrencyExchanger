package avlyakulov.timur.dao;

import avlyakulov.timur.model.Currency;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public interface CurrencyDao extends CrudDao<Currency> {

    Currency findCurrencyByCode(String code);

    Connection getConnection() throws SQLException;
}
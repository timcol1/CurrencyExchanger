package avlyakulov.timur.dao;

import avlyakulov.timur.connection.ConnectionBuilder;
import avlyakulov.timur.model.Currency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyDaoImpl implements CurrencyDao {

    private ConnectionBuilder connectionBuilder;

    public void setConnectionBuilder(ConnectionBuilder connectionBuilder) {
        this.connectionBuilder = connectionBuilder;
    }

    private Connection getConnection() throws SQLException {
        return connectionBuilder.getConnection();
    }

    @Override
    public Optional<List<Currency>> findAll() {
        final String findAllQuery = "SELECT * FROM Currencies";
        List<Currency> currencies = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findAllQuery)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Currency currency = new Currency(
                        Integer.parseInt(resultSet.getString("ID")),
                        resultSet.getString("Code"),
                        resultSet.getString("FullName"),
                        resultSet.getString("Sign")
                );
                currencies.add(currency);
            }
            return Optional.of(currencies);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Currency> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Optional<Currency> update(Integer id, Currency currency) {
        return Optional.empty();
    }

    @Override
    public Optional<Currency> findByCode(String code) {
        return Optional.empty();
    }
}
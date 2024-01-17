package avlyakulov.timur.dao;

import avlyakulov.timur.model.Currency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyDao {

    //todo сделать абстрактным чтоб не привязываться к конкретной реализации
    private final String findAllQuery = "SELECT * FROM Currencies";

    private ConnectionBuilder connectionBuilder;

    public void setConnectionBuilder(ConnectionBuilder connectionBuilder) {
        this.connectionBuilder = connectionBuilder;
    }

    private Connection getConnection() throws SQLException {
        return connectionBuilder.getConnection();
    }


    public Optional<List<Currency>> findAll() {
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
}
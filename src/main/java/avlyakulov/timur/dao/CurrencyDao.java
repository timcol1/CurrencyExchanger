package avlyakulov.timur.dao;

import avlyakulov.timur.model.Currency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDao {

    private final String DB_URL = "jdbc:sqlite:sqlite/users.db";
    private final String findAllQuery = "SELECT * FROM Currencies";


    public List<Currency> findAll() {
        List<Currency> currencies = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            PreparedStatement preparedStatement = connection.prepareStatement(findAllQuery);
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
            return currencies;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
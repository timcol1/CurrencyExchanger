package avlyakulov.timur.dao;

import avlyakulov.timur.connection.ConnectionDB;
import avlyakulov.timur.custom_exception.CurrencyAlreadyExistsException;
import avlyakulov.timur.model.Currency;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class CurrencyDaoImpl implements CurrencyDao {

    private DeploymentEnvironment deploymentEnvironment;

    public Connection getConnection() {
        return ConnectionDB.getConnection(deploymentEnvironment);
    }

    public CurrencyDaoImpl(DeploymentEnvironment deploymentEnvironment) {
        this.deploymentEnvironment = deploymentEnvironment;
    }

    @Override
    public List<Currency> findAll() {
        final String findAllQuery = "SELECT * FROM Currencies;";

        List<Currency> currencies = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findAllQuery)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                currencies.add(setCurrency(resultSet));
            }
            return currencies;
        } catch (SQLException e) {
            log.error("Error with db");
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Currency> findCurrencyByCode(String code) {
        String findByCodeQuery = "SELECT * FROM Currencies WHERE Code = ?;";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findByCodeQuery)) {
            preparedStatement.setString(1, code);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(setCurrency(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            log.error("Error with db");
            throw new RuntimeException(e);
        }
    }

    @Override
    public Currency create(Currency currency) {
        String createCurrencyQuery = "INSERT INTO Currencies(Code, FullName , Sign) VALUES (?, ?, ?);";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(createCurrencyQuery, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, currency.getCode());
            preparedStatement.setString(2, currency.getName());
            preparedStatement.setString(3, currency.getSign());

            preparedStatement.executeUpdate();

            ResultSet savedCurrency = preparedStatement.getGeneratedKeys();
            savedCurrency.next();
            currency.setId(savedCurrency.getLong(1));

            return currency;
        } catch (SQLException e) {
            throw new CurrencyAlreadyExistsException("Currency with such code " + currency.getCode() + " already exists");
        }
    }

    private Currency setCurrency(ResultSet resultSet) throws SQLException {
        return new Currency(
                resultSet.getLong("ID"),
                resultSet.getString("Code"),
                resultSet.getString("FullName"),
                resultSet.getString("Sign")
        );
    }
}
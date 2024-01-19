package avlyakulov.timur.dao;

import avlyakulov.timur.connection.ConnectionBuilder;
import avlyakulov.timur.model.Currency;
import avlyakulov.timur.model.ExchangeRate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExchangeRateDaoImpl implements ExchangeRateDao {

    private ConnectionBuilder connectionBuilder;

    public void setConnectionBuilder(ConnectionBuilder connectionBuilder) {
        this.connectionBuilder = connectionBuilder;
    }

    private Connection getConnection() throws SQLException {
        return connectionBuilder.getConnection();
    }


    @Override
    public List<ExchangeRate> findAll() {
        final String findAllQuery = "SELECT er.ID,\n" +
                "       bc.ID,\n" +
                "       bc.Code,\n" +
                "       bc.FullName,\n" +
                "       bc.Sign,\n" +
                "       tc.ID,\n" +
                "       tc.Code,\n" +
                "       tc.FullName,\n" +
                "       tc.Sign,\n" +
                "       Rate\n" +
                "FROM ExchangeRates er\n" +
                "         INNER JOIN Currencies AS bc ON er.BaseCurrencyId = bc.ID\n" +
                "         INNER JOIN Currencies AS tc ON er.TargetCurrencyId = tc.ID;\n";

        List<ExchangeRate> exchangeRates = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findAllQuery)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ExchangeRate currency = new ExchangeRate(
                        new Currency(),
                        new Currency(),
                        double rate;
                );
                exchangeRates.add(currency);
            }
            return exchangeRates;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<ExchangeRate> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Optional<ExchangeRate> update(Integer id, ExchangeRate exchangeRate) {
        return Optional.empty();
    }

    @Override
    public ExchangeRate create(ExchangeRate exchangeRate) {
        return null;
    }

    @Override
    public ExchangeRate findByCodes(String baseCurrencyCode, String targetCurrencyCode) {
        return null;
    }
}
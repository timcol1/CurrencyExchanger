package avlyakulov.timur.dao;

import avlyakulov.timur.connection.ConnectionBuilder;
import avlyakulov.timur.model.Currency;
import avlyakulov.timur.model.ExchangeRate;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ExchangeRateDaoImpl implements ExchangeRateDao {

    private ConnectionBuilder connectionBuilder;

    @Override
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
            Currency baseCurrency;
            Currency targetCurrency;
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                baseCurrency = new Currency(
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                );

                targetCurrency = new Currency(
                        resultSet.getInt(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9)
                );
                ExchangeRate currency = new ExchangeRate(
                        resultSet.getInt(1),
                        baseCurrency,
                        targetCurrency,
                        resultSet.getBigDecimal(10)
                );
                exchangeRates.add(currency);
            }
            return exchangeRates;
        } catch (SQLException e) {
            log.error("Error with db");
            throw new RuntimeException(e);
        }
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

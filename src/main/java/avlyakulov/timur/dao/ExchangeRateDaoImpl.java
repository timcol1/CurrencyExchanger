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
import java.util.Optional;

@Slf4j
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
                "       bc.ID AS BaseCurrencyId,\n" +
                "       bc.Code AS BaseCurrencyCode,\n" +
                "       bc.FullName AS BaseCurrencyFullName,\n" +
                "       bc.Sign AS BaseCurrencySign,\n" +
                "       tc.ID AS TargetCurrencyId,\n" +
                "       tc.Code AS TargetCurrencyCode,\n" +
                "       tc.FullName AS TargetCurrencyFullName,\n" +
                "       tc.Sign AS TargetCurrencySign,\n" +
                "       Rate\n" +
                "FROM ExchangeRates er\n" +
                "         INNER JOIN Currencies AS bc ON er.BaseCurrencyId = bc.ID\n" +
                "         INNER JOIN Currencies AS tc ON er.TargetCurrencyId = tc.ID;\n";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findAllQuery)) {
            Currency baseCurrency;
            Currency targetCurrency;
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ExchangeRate> exchangeRates = new ArrayList<>();
            while (resultSet.next()) {
                baseCurrency = new Currency(
                        resultSet.getInt("BaseCurrencyId"),
                        resultSet.getString("BaseCurrencyCode"),
                        resultSet.getString("BaseCurrencyFullName"),
                        resultSet.getString("BaseCurrencySign")
                );

                targetCurrency = new Currency(
                        resultSet.getInt("TargetCurrencyId"),
                        resultSet.getString("TargetCurrencyCode"),
                        resultSet.getString("TargetCurrencyFullName"),
                        resultSet.getString("TargetCurrencySign")
                );
                ExchangeRate exchangeRate = new ExchangeRate(
                        resultSet.getInt("ID"),
                        baseCurrency,
                        targetCurrency,
                        resultSet.getBigDecimal("Rate")
                );
                exchangeRates.add(exchangeRate);
            }
            return exchangeRates;
        } catch (SQLException e) {
            log.error("Error with db");
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<ExchangeRate> findByCodes(String baseCurrencyCode, String targetCurrencyCode) {
        final String findExchangeRateQuery = "SELECT er.ID,\n" +
                "       bc.ID       AS BaseCurrencyId,\n" +
                "       bc.Code     AS BaseCurrencyCode,\n" +
                "       bc.FullName AS BaseCurrencyFullName,\n" +
                "       bc.Sign     AS BaseCurrencySign,\n" +
                "       tc.ID       AS TargetCurrencyId,\n" +
                "       tc.Code     AS TargetCurrencyCode,\n" +
                "       tc.FullName AS TargetCurrencyFullName,\n" +
                "       tc.Sign     AS TargetCurrencySign,\n" +
                "       Rate\n" +
                "FROM ExchangeRates er\n" +
                "         INNER JOIN Currencies AS bc ON er.BaseCurrencyId = bc.ID\n" +
                "         INNER JOIN Currencies AS tc ON er.TargetCurrencyId = tc.ID\n" +
                "WHERE BaseCurrencyCode = ? AND TargetCurrencyCode = ?;";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findExchangeRateQuery)) {
            preparedStatement.setString(1, baseCurrencyCode);
            preparedStatement.setString(2, targetCurrencyCode);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Currency baseCurrency = new Currency(
                        resultSet.getInt("BaseCurrencyId"),
                        resultSet.getString("BaseCurrencyCode"),
                        resultSet.getString("BaseCurrencyFullName"),
                        resultSet.getString("BaseCurrencySign")
                );

                Currency targetCurrency = new Currency(
                        resultSet.getInt("TargetCurrencyId"),
                        resultSet.getString("TargetCurrencyCode"),
                        resultSet.getString("TargetCurrencyFullName"),
                        resultSet.getString("TargetCurrencySign")
                );
                ExchangeRate exchangeRate = new ExchangeRate(
                        resultSet.getInt("ID"),
                        baseCurrency,
                        targetCurrency,
                        resultSet.getBigDecimal("Rate")
                );
                return Optional.of(exchangeRate);
            }
        } catch (SQLException e) {
            log.error("Error with db");
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public ExchangeRate create(ExchangeRate exchangeRate) {
        return null;
    }
}

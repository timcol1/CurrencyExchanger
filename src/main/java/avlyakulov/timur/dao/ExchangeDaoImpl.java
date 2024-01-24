package avlyakulov.timur.dao;

import avlyakulov.timur.connection.ConnectionBuilder;
import avlyakulov.timur.model.Currency;
import avlyakulov.timur.model.Exchange;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class ExchangeDaoImpl implements ExchangeDao {
    private ConnectionBuilder connectionBuilder;

    public void setConnectionBuilder(ConnectionBuilder connectionBuilder) {
        this.connectionBuilder = connectionBuilder;
    }

    private Connection getConnection() throws SQLException {
        return connectionBuilder.getConnection();
    }

    @Override
    public Exchange exchange(String baseCurrencyCode, String targetCurrencyCode, BigDecimal amount) {
        final String exchangeQuery = "SELECT " +
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
             PreparedStatement preparedStatement = connection.prepareStatement(exchangeQuery)) {
            preparedStatement.setString(1, baseCurrencyCode);
            preparedStatement.setString(2, targetCurrencyCode);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return getExchangeFromResultSetAB(resultSet, amount);//AB
            } else {
                preparedStatement.setString(1, targetCurrencyCode);
                preparedStatement.setString(2, baseCurrencyCode);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return getExchangeFromResultSetBA(resultSet, amount);//BA

                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            log.error("Error with db");
            throw new RuntimeException(e);
        }
    }

    public Exchange getExchangeFromResultSetAB(ResultSet resultSet, BigDecimal amount) throws SQLException {
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
        BigDecimal rate = resultSet.getBigDecimal("Rate");
        BigDecimal convertedAmount = rate.multiply(amount);
        return new Exchange(
                baseCurrency,
                targetCurrency,
                rate,
                amount,
                convertedAmount
        );
    }

    public Exchange getExchangeFromResultSetBA(ResultSet resultSet, BigDecimal amount) throws SQLException {
        Currency baseCurrency = new Currency(
                resultSet.getInt("TargetCurrencyId"),
                resultSet.getString("TargetCurrencyCode"),
                resultSet.getString("TargetCurrencyFullName"),
                resultSet.getString("TargetCurrencySign")
        );
        Currency targetCurrency = new Currency(
                resultSet.getInt("BaseCurrencyId"),
                resultSet.getString("BaseCurrencyCode"),
                resultSet.getString("BaseCurrencyFullName"),
                resultSet.getString("BaseCurrencySign")
        );
        BigDecimal rate = BigDecimal.ONE.divide(resultSet.getBigDecimal("Rate"), 3, RoundingMode.HALF_UP);
        BigDecimal convertedAmount = rate.multiply(amount);
        return new Exchange(
                baseCurrency,
                targetCurrency,
                rate,
                amount,
                convertedAmount
        );
    }
}
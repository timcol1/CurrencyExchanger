package avlyakulov.timur.dao;

import avlyakulov.timur.connection.DataSource;
import avlyakulov.timur.custom_exception.ExchangeRateCurrencyPairNotFoundException;
import avlyakulov.timur.dao.ExchangeDao;
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

        String exchangeRateUSDA = "SELECT TargetCurrencyId AS ID, Code, FullName, Sign, Rate FROM ExchangeRates AS er INNER JOIN Currencies AS c ON er.TargetCurrencyId = c.ID\n" +
                "WHERE BaseCurrencyId = (SELECT ID FROM Currencies WHERE Code = 'USD')\n" +
                "  AND TargetCurrencyId = (SELECT ID FROM Currencies WHERE Code = ?);";
        String exchangeRateUSDB = "SELECT TargetCurrencyId AS ID, Code, FullName, Sign, Rate FROM ExchangeRates AS er INNER JOIN Currencies AS c ON er.TargetCurrencyId = c.ID\n" +
                "WHERE BaseCurrencyId = (SELECT ID FROM Currencies WHERE Code = 'USD')\n" +
                "  AND TargetCurrencyId = (SELECT ID FROM Currencies WHERE Code = ?);";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(exchangeQuery);
             PreparedStatement preparedStatementUSDA = connection.prepareStatement(exchangeRateUSDA);
             PreparedStatement preparedStatementUSDB = connection.prepareStatement(exchangeRateUSDB)) {

            preparedStatement.setString(1, baseCurrencyCode);
            preparedStatement.setString(2, targetCurrencyCode);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                log.info("Completed the exchange AB");
                return getExchangeFromResultSetAB(resultSet, amount);//AB
            } else {
                preparedStatement.setString(1, targetCurrencyCode);
                preparedStatement.setString(2, baseCurrencyCode);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    log.info("Completed the exchange BA");
                    return getExchangeFromResultSetBA(resultSet, amount);//BA
                } else {
                    preparedStatementUSDA.setString(1, baseCurrencyCode);
                    preparedStatementUSDB.setString(1, targetCurrencyCode);
                    ResultSet currencyA = preparedStatementUSDA.executeQuery();
                    ResultSet currencyB = preparedStatementUSDB.executeQuery();
                    return getExchangeFromResultSetUSDAB(currencyA, currencyB, amount);//USD-A and USD-B = AB
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

    public Exchange getExchangeFromResultSetUSDAB(ResultSet currencyA, ResultSet currencyB, BigDecimal amount) throws SQLException {
        if (currencyA.next() && currencyB.next()) {
            log.info("Completed the exchange from USD-A and USD-B = AB");
            Currency baseCurrency = new Currency(
                    currencyA.getInt("ID"),
                    currencyA.getString("Code"),
                    currencyA.getString("FullName"),
                    currencyA.getString("Sign")
            );
            Currency targetCurrency = new Currency(
                    currencyB.getInt("ID"),
                    currencyB.getString("Code"),
                    currencyB.getString("FullName"),
                    currencyB.getString("Sign")
            );
            BigDecimal rateUSDA = currencyA.getBigDecimal("Rate");
            BigDecimal rateUSDB = currencyB.getBigDecimal("Rate");
            BigDecimal rate = rateUSDB.divide(rateUSDA, 3, RoundingMode.HALF_UP);
            BigDecimal convertedAmount = rate.multiply(amount);

            return new Exchange(
                    baseCurrency,
                    targetCurrency,
                    rate,
                    amount,
                    convertedAmount
            );
        } else {
            throw new ExchangeRateCurrencyPairNotFoundException("The exchange rate with such code pair wasn't found");
        }
    }
}
package avlyakulov.timur.dao;

import avlyakulov.timur.connection.DeploymentEnvironment;
import avlyakulov.timur.custom_exception.ExchangeRateAlreadyExistsException;
import avlyakulov.timur.custom_exception.ExchangeRateCurrencyCodePairException;
import avlyakulov.timur.custom_exception.ExchangeRateCurrencyPairNotFoundException;
import avlyakulov.timur.model.Currency;
import avlyakulov.timur.model.ExchangeRate;
import lombok.extern.slf4j.Slf4j;
import org.sqlite.SQLiteErrorCode;
import org.sqlite.SQLiteException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class ExchangeRateDaoImpl extends JDBCDao implements ExchangeRateDao {

    public ExchangeRateDaoImpl(DeploymentEnvironment deploymentEnvironment) {
        super(deploymentEnvironment);
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

            ResultSet resultSet = preparedStatement.executeQuery();

            List<ExchangeRate> exchangeRates = new ArrayList<>();

            while (resultSet.next()) {
                exchangeRates.add(setExchangeRate(resultSet));
            }
            return exchangeRates;
        } catch (SQLException e) {
            log.error("Error with db");
            throw new RuntimeException(e);
        }
    }

    @Override
    public ExchangeRate findByCodes(String baseCurrencyCode, String targetCurrencyCode) {
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
                return setExchangeRate(resultSet);
            } else {
                throw new ExchangeRateCurrencyPairNotFoundException("The exchange rate with such code pair " + baseCurrencyCode + targetCurrencyCode + " doesn't exist");
            }
        } catch (SQLException e) {
            log.error("Error with db");
            throw new RuntimeException(e);
        }
    }

    @Override
    public ExchangeRate create(ExchangeRate exchangeRate) {
        final String createExchangeRateByPairCodeAndRate = "INSERT INTO ExchangeRates(BaseCurrencyId, TargetCurrencyId, Rate)\n" +
                "values ((SELECT ID FROM Currencies WHERE Code = ?),\n" +
                "        (SELECT ID FROM Currencies WHERE Code = ?),\n" +
                "        ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(createExchangeRateByPairCodeAndRate, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, exchangeRate.getBaseCurrency().getCode());
            preparedStatement.setString(2, exchangeRate.getTargetCurrency().getCode());
            preparedStatement.setBigDecimal(3, exchangeRate.getRate());

            preparedStatement.executeUpdate();

            return findByCodes(exchangeRate.getBaseCurrency().getCode(), exchangeRate.getTargetCurrency().getCode());
        } catch (SQLiteException e) {
            log.error("Error code is " + e.getResultCode());
            SQLiteErrorCode resultCode = e.getResultCode();
            if (resultCode.equals(SQLiteErrorCode.SQLITE_CONSTRAINT_UNIQUE)) {
                throw new ExchangeRateAlreadyExistsException("Exchange rate with such code pair already exists");
            } else {
                throw new ExchangeRateCurrencyCodePairException("One or two currencies doesn't exits");
            }
        } catch (SQLException e) {
            log.error("Error with db");
            throw new RuntimeException(e);
        }
    }


    @Override
    public ExchangeRate update(String baseCurrencyCode, String targetCurrencyCode, BigDecimal rate) {
        final String updateRateQuery = "update ExchangeRates\n" +
                "set rate = ?\n" +
                "where BaseCurrencyId = (select ID from Currencies where code = ?) \n" +
                "  and TargetCurrencyId = (select ID from Currencies where code = ?);";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateRateQuery, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setBigDecimal(1, rate);
            preparedStatement.setString(2, baseCurrencyCode);
            preparedStatement.setString(3, targetCurrencyCode);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return findByCodes(baseCurrencyCode, targetCurrencyCode);
            } else {
                throw new ExchangeRateCurrencyPairNotFoundException("The exchange rate with such code pair " + baseCurrencyCode + targetCurrencyCode + " doesn't exist");
            }
        } catch (SQLException e) {
            log.error("Error with db");
            throw new RuntimeException(e);
        }
    }

    private ExchangeRate setExchangeRate(ResultSet resultSet) throws SQLException {
        Currency baseCurrency = new Currency(
                resultSet.getLong("BaseCurrencyId"),
                resultSet.getString("BaseCurrencyCode"),
                resultSet.getString("BaseCurrencyFullName"),
                resultSet.getString("BaseCurrencySign")
        );

        Currency targetCurrency = new Currency(
                resultSet.getLong("TargetCurrencyId"),
                resultSet.getString("TargetCurrencyCode"),
                resultSet.getString("TargetCurrencyFullName"),
                resultSet.getString("TargetCurrencySign")
        );
        return new ExchangeRate(
                resultSet.getLong("ID"),
                baseCurrency,
                targetCurrency,
                resultSet.getBigDecimal("Rate")
        );
    }

    @Override
    public Optional<ExchangeRate> findById(long id) {
        return Optional.empty();
    }

    @Override
    public ExchangeRate update(ExchangeRate exchangeRate) {
        return null;
    }
}
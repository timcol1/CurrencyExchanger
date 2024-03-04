package avlyakulov.timur.service.impl;

import avlyakulov.timur.connection.DeploymentEnvironment;
import avlyakulov.timur.custom_exception.ExchangeRateCurrencyPairNotFoundException;
import avlyakulov.timur.dao.ExchangeRateDao;
import avlyakulov.timur.dao.ExchangeRateDaoImpl;
import avlyakulov.timur.dto.exchange.ExchangeResponse;
import avlyakulov.timur.mapper.ExchangeRateMapper;
import avlyakulov.timur.model.Currency;
import avlyakulov.timur.model.ExchangeRate;
import avlyakulov.timur.service.ExchangeRateService;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Slf4j
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExchangeRateDao exchangeRateDao = new ExchangeRateDaoImpl(DeploymentEnvironment.PROD);


    private final String baseCurrencyCodeUSD = "USD";

    private final ExchangeRateMapper exchangeRateMapper = new ExchangeRateMapper();


    public List<ExchangeRate> findAll() {
        return exchangeRateDao.findAll();
    }

    public ExchangeRate findByCodes(String currencyPairCode) {
        String baseCurrencyCode = currencyPairCode.substring(0, 3);
        String targetCurrencyCode = currencyPairCode.substring(3);

        return exchangeRateDao.findByCodes(baseCurrencyCode, targetCurrencyCode);
    }

    public ExchangeRate createExchangeRate(ExchangeRate exchangeRate) {
        return exchangeRateDao.create(exchangeRate);
    }


    public ExchangeRate updateExchangeRate(String currencyPairCode, BigDecimal updatedRate) {
        String baseCurrencyCode = currencyPairCode.substring(0, 3);
        String targetCurrencyCode = currencyPairCode.substring(3);

        return exchangeRateDao.update(baseCurrencyCode, targetCurrencyCode, updatedRate);
    }

    public ExchangeResponse exchange(String baseCurrencyCode, String targetCurrencyCode, BigDecimal amount) {
        try {
            ExchangeRate exchangeRateAB = exchangeRateDao.findByCodes(baseCurrencyCode, targetCurrencyCode);
            log.info("Completed the exchange AB");
            return exchangeAB(exchangeRateAB, amount);
        } catch (ExchangeRateCurrencyPairNotFoundException e) {
            try {
                ExchangeRate exchangeRateBA = exchangeRateDao.findByCodes(targetCurrencyCode, baseCurrencyCode);
                log.info("Completed the exchange BA");
                return exchangeBA(exchangeRateBA, amount);
            } catch (ExchangeRateCurrencyPairNotFoundException e1) {
                try {
                    ExchangeRate exchangeRateUSDA = exchangeRateDao.findByCodes(baseCurrencyCodeUSD, baseCurrencyCode);
                    ExchangeRate exchangeRateUSDB = exchangeRateDao.findByCodes(baseCurrencyCodeUSD, targetCurrencyCode);
                    log.info("Completed the exchange from USD-A and USD-B = AB");
                    return exchangeUSDAUSDB(exchangeRateUSDA, exchangeRateUSDB, amount);
                } catch (ExchangeRateCurrencyPairNotFoundException e2) {
                    throw new ExchangeRateCurrencyPairNotFoundException("The exchange rate with such code pair " + baseCurrencyCode + targetCurrencyCode + " doesn't exist");
                }
            }
        }
    }

    public ExchangeResponse exchangeAB(ExchangeRate exchangeRate, BigDecimal amount) {
        BigDecimal convertedAmount = amount.multiply(exchangeRate.getRate());
        return exchangeRateMapper.mapToExchangeResponse(exchangeRate, amount, convertedAmount);
    }

    public ExchangeResponse exchangeBA(ExchangeRate exchangeRateBA, BigDecimal amount) {
        BigDecimal rateBA = BigDecimal.ONE.divide(exchangeRateBA.getRate(), 2, RoundingMode.HALF_UP);
        ExchangeRate exchangeRate = new ExchangeRate(exchangeRateBA.getTargetCurrency(), exchangeRateBA.getBaseCurrency(), rateBA);
        BigDecimal convertedAmount = amount.multiply(rateBA);
        return exchangeRateMapper.mapToExchangeResponse(exchangeRate, amount, convertedAmount);
    }

    public ExchangeResponse exchangeUSDAUSDB(ExchangeRate exchangeRateUSDA, ExchangeRate exchangeRateUSDB, BigDecimal amount) {
        Currency baseCurrency = exchangeRateUSDA.getTargetCurrency();
        Currency targetCurrency = exchangeRateUSDB.getTargetCurrency();
        BigDecimal rateUSDA = exchangeRateUSDA.getRate();
        BigDecimal rateUSDB = exchangeRateUSDB.getRate();
        BigDecimal rate = rateUSDB.divide(rateUSDA, 2, RoundingMode.HALF_UP);
        BigDecimal convertedAmount = rate.multiply(amount);
        ExchangeRate exchangeRate = new ExchangeRate(baseCurrency, targetCurrency, rate);
        return exchangeRateMapper.mapToExchangeResponse(exchangeRate, amount, convertedAmount);
    }
}
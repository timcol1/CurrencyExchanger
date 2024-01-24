package avlyakulov.timur.mapper;

import avlyakulov.timur.dto.exchange.ExchangeRateRequest;
import avlyakulov.timur.dto.exchange.ExchangeRateResponse;
import avlyakulov.timur.model.Currency;
import avlyakulov.timur.model.ExchangeRate;

public class ExchangeRateMapper implements ResponseMapper<ExchangeRate, ExchangeRateResponse>, RequestMapper<ExchangeRateRequest, ExchangeRate> {

    private final CurrencyMapper currencyMapper = new CurrencyMapper();


    @Override
    public ExchangeRateResponse mapToResponse(ExchangeRate exchangeRate) {
        return new ExchangeRateResponse(
                exchangeRate.getId(),
                currencyMapper.mapToResponse(exchangeRate.getBaseCurrency()),
                currencyMapper.mapToResponse(exchangeRate.getTargetCurrency()),
                exchangeRate.getRate()
        );
    }

    @Override
    public ExchangeRate mapToEntity(ExchangeRateRequest exchangeRateRequest) {
        Currency baseCurrency = new Currency(exchangeRateRequest.getBaseCurrencyCode());
        Currency targetCurrency = new Currency(exchangeRateRequest.getTargetCurrencyCode());

        return new ExchangeRate(
                baseCurrency,
                targetCurrency,
                exchangeRateRequest.getRate()
        );
    }
}
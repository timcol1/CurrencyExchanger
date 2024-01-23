package avlyakulov.timur.mapper;

import avlyakulov.timur.dto.exchange.ExchangeRateResponse;
import avlyakulov.timur.model.ExchangeRate;

public class ExchangeRateMapper implements ResponseMapper<ExchangeRate, ExchangeRateResponse> {

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
}
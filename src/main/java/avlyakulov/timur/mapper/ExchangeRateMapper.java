package avlyakulov.timur.mapper;

import avlyakulov.timur.dto.exchange.ExchangeRateResponse;
import avlyakulov.timur.model.ExchangeRate;

public class ExchangeRateMapper implements ResponseMapper<ExchangeRate, ExchangeRateResponse> {

    @Override
    public ExchangeRateResponse mapToResponse(ExchangeRate exchangeRate) {
        return new ExchangeRateResponse(
                exchangeRate.getId(),
                exchangeRate.getBaseCurrency(),
                exchangeRate.getTargetCurrency(),
                exchangeRate.getRate()
        );
    }
}

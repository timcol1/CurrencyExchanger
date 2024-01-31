package avlyakulov.timur.mapper;

import avlyakulov.timur.dto.currency.CurrencyRequest;
import avlyakulov.timur.dto.currency.CurrencyResponse;
import avlyakulov.timur.model.Currency;

public class CurrencyMapper implements RequestMapper<CurrencyRequest, Currency>, ResponseMapper<Currency, CurrencyResponse> {

    @Override
    public Currency mapToEntity(CurrencyRequest currencyRequest) {
        return new Currency(
                currencyRequest.getCode(),
                currencyRequest.getFullName(),
                currencyRequest.getSign()
        );
    }


    @Override
    public CurrencyResponse mapToResponse(Currency currency) {
        return new CurrencyResponse(
                currency.getId(),
                currency.getCode(),
                currency.getFullName(),
                currency.getSign()
        );
    }
}
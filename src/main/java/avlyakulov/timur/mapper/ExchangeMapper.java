package avlyakulov.timur.mapper;

import avlyakulov.timur.dto.exchange.ExchangeResponse;
import avlyakulov.timur.model.Exchange;

public class ExchangeMapper implements ResponseMapper<Exchange, ExchangeResponse> {

    private final CurrencyMapper currencyMapper = new CurrencyMapper();

    @Override
    public ExchangeResponse mapToResponse(Exchange exchange) {
        return new ExchangeResponse(
                currencyMapper.mapToResponse(exchange.getBaseCurrency()),
                currencyMapper.mapToResponse(exchange.getTargetCurrency()),
                exchange.getRate(),
                exchange.getAmount(),
                exchange.getConvertedAmount()
        );
    }
}

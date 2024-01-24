package avlyakulov.timur.mapper;

import avlyakulov.timur.dto.exchange.ExchangeResponse;
import avlyakulov.timur.model.Exchange;

public class ExchangeMapper implements ResponseMapper<Exchange, ExchangeResponse> {

    @Override
    public ExchangeResponse mapToResponse(Exchange exchange) {
        return new ExchangeResponse(
                exchange.getBaseCurrency(),
                exchange.getTargetCurrency(),
                exchange.getRate(),
                exchange.getAmount(),
                exchange.getConvertedAmount()
        );
    }
}

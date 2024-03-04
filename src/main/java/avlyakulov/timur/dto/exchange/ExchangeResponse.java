package avlyakulov.timur.dto.exchange;

import avlyakulov.timur.dto.currency.CurrencyResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ExchangeResponse {

    private CurrencyResponse baseCurrency;

    private CurrencyResponse targetCurrency;

    private BigDecimal rate;

    private BigDecimal amount;

    private BigDecimal convertedAmount;
}
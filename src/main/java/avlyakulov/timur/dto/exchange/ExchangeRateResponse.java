package avlyakulov.timur.dto.exchange;

import avlyakulov.timur.dto.currency.CurrencyResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ExchangeRateResponse {

    private Long id;

    private CurrencyResponse baseCurrency;

    private CurrencyResponse targetCurrency;

    private BigDecimal rate;
}
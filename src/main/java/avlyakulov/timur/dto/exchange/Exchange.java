package avlyakulov.timur.dto.exchange;

import avlyakulov.timur.model.Currency;
import avlyakulov.timur.model.ExchangeRate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class Exchange {

    Currency baseCurrency;

    Currency targetCurrency;

    private BigDecimal amount;

    private BigDecimal convertedAmount;
}
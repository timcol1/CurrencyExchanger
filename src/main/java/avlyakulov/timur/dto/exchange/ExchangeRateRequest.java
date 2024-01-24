package avlyakulov.timur.dto.exchange;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ExchangeRateRequest {

    private String baseCurrencyCode;

    private String targetCurrencyCode;

    private BigDecimal rate;

}
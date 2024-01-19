package avlyakulov.timur.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ExchangeRate {

    private int id;

    private Currency baseCurrency;

    private Currency targetCurrency;

    private double rate;

    public ExchangeRate(Currency baseCurrency, Currency targetCurrency, double rate) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
    }
}

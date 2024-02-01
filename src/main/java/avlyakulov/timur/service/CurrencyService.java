package avlyakulov.timur.service;

import avlyakulov.timur.model.Currency;

import java.util.List;

public interface CurrencyService {

    List<Currency> findAll();

    Currency findByCode(String code);

    Currency createCurrency(Currency currency);
}
SELECT er.ID,
       bc.ID       AS BaseCurrencyId,
       bc.Code     AS BaseCurrencyCode,
       bc.FullName AS BaseCurrencyFullName,
       bc.Sign     AS BaseCurrencySign,
       tc.ID       AS TargetCurrencyId,
       tc.Code     AS TargetCurrencyCode,
       tc.FullName AS TargetCurrencyFullName,
       tc.Sign     AS TargetCurrencySign,
       Rate
FROM ExchangeRates er
         INNER JOIN Currencies AS bc ON er.BaseCurrencyId = bc.ID
         INNER JOIN Currencies AS tc ON er.TargetCurrencyId = tc.ID
WHERE BaseCurrencyCode = 'UAH' AND TargetCurrencyCode = 'USD';
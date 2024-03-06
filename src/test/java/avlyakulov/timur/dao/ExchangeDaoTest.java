package avlyakulov.timur.dao;

import avlyakulov.timur.connection.DeploymentEnvironment;

class ExchangeDaoTest {

    private ExchangeRateDao exchangeRateDao = new ExchangeRateDaoImpl(DeploymentEnvironment.TEST);

//    ExchangeDaoTest() throws SQLException {
//    }
//
//    @BeforeEach
//    void setUpEach() throws URISyntaxException, IOException, SQLException {
//        DBState.createDB();
//    }
//
//    @AfterEach()
//    void cleanUp() throws SQLException {
//        DBState.cleanDB();
//    }
//    //MethodName_ExpectedBehavior_StateUnderTest
//
//    @Test
//    void exchange_exchangeAB_currencyCodesExists() {
//        String baseCurrencyCode = "USD";
//        String targetCurrencyCode = "UAH";
//        BigDecimal amount = BigDecimal.TEN;
//
//        ExchangeResponse exchange = exchangeRateDao.(baseCurrencyCode, targetCurrencyCode, amount);
//
//        Assertions.assertNotNull(exchange);
//        Assertions.assertEquals(amount, exchange.getAmount());
//        Assertions.assertEquals(new BigDecimal("37.65"), exchange.getRate());
//        Assertions.assertEquals(new BigDecimal("376.50"), exchange.getConvertedAmount());
//    }
//
//    @Test
//    void exchange_exchangeBA_currencyCodesExists() {
//        String baseCurrencyCode = "UAH";
//        String targetCurrencyCode = "USD";
//        BigDecimal amount = BigDecimal.TEN;
//
//        Exchange exchange = exchangeDao.exchange(baseCurrencyCode, targetCurrencyCode, amount);
//
//        Assertions.assertNotNull(exchange);
//        Assertions.assertEquals(amount, exchange.getAmount());
//        Assertions.assertEquals(new BigDecimal("0.03"), exchange.getRate());
//        Assertions.assertEquals(new BigDecimal("0.30"), exchange.getConvertedAmount());
//    }
//
//    @Test
//    void exchange_exchangeUSDAUSDB_currencyCodesExists() {
//        String baseCurrencyCode = "EUR";
//        String targetCurrencyCode = "UAH";
//        BigDecimal amount = BigDecimal.TEN;
//
//        Exchange exchange = exchangeDao.exchange(baseCurrencyCode, targetCurrencyCode, amount);
//
//        Assertions.assertNotNull(exchange);
//        Assertions.assertEquals(amount, exchange.getAmount());
//        Assertions.assertEquals(new BigDecimal("40.48"), exchange.getRate());
//        Assertions.assertEquals(new BigDecimal("404.80"), exchange.getConvertedAmount());
//    }
//
//    @Test
//    void exchange_throwException_currencyPairCodesNotExist() {
//        String baseCurrencyCode = "USD";
//        String targetCurrencyCode = "PLN";
//        BigDecimal amount = BigDecimal.TEN;
//
//        Assertions.assertThrows(ExchangeRateCurrencyPairNotFoundException.class, () -> exchangeDao.exchange(baseCurrencyCode, targetCurrencyCode, amount));
//    }
}
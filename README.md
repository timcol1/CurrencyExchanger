# Currency Exchanger

## Description
`Currency Exchanger` - It is Java Servlet App with Unit tests REST API for currency exchange.The main functions include managing currencies, setting exchange rates and calculating the amount of exchange at direct, reverse and cross rates.
## Project Stack
* Rest API
* Servlets
* Java EE
* SQLite
* Slf4j
* Jackson
* JUnit 5
* Mockito
* Hikari Pool

## Technologies 
The project is developed using Jakarta Servlets I also wrote Unit tests for this app and it is supports Tomcat 10 server. I used sql database as SQLite. I also used Hikari pool to make connection to Database. I

## Settings and Launch
For Launch `Currency Exchanger` complete 3 steps:
1. Clone the repository.
2. Collect war artefact, using command `mvn clean package`.
3. Launch the application using tomcat in intellij idea or copy war file to tomcat in web app and in tomcat/bin launch startup script.

## Using API
- `GET /currencies` - getting all available currencies.
- `GET /currency/USD` - getting currency by unique code.
- `POST /currencies` - In order to create currency you need to sent post request with `x-www-form-urlencoded` fields. Parameters `name`, `code`, `sign`. Example:
```
POST localhost:8080/CurrencyExchanger-1.0/currencies

Content-Type: application/x-www-form-urlencoded

name=US Dollar
code=USD
sign=$
```
- `GET /exchangeRates` - getting all available exchange rates.
- `GET /exchangeRate/USDUAH` - getting available exchange rate by code pair.
- `POST /exchangeRates` - In order to create exchange rate you need to sent post request with `x-www-form-urlencoded` fields. Parameters `baseCurrencyCode`, `targetCurrencyCode`, `rate`. Example:
```
POST localhost:8080/CurrencyExchanger-1.0/exchangeRates

Content-Type: application/x-www-form-urlencoded

baseCurrencyCode=EUR
targetCurrencyCode=UAH
rate=40.41
```
- `PATCH /exchangeRate/USDUAH` - In order to update exchange rate you need to sent post request with `x-www-form-urlencoded` fields. Parameters `rate`. Example:
```
PATCH localhost:8080/CurrencyExchanger-1.0/exchangeRate/USDUAH

Content-Type: application/x-www-form-urlencoded

rate=37.65  
```
- `GET /exchange?from=USD&to=UAH&amount=2.99` - getting exchange.
We can get exchange from A to B currency, reverse from B to A, and cross exchange USD A and USD B = A B

Examples of responses:

GET CURRENCY
```
{
    "id": 2,
    "code": "USD",
    "name": "US Dollar",
    "sign": "$"
}
```
GET Exchange Rate
```
{
    "id": 3,
    "baseCurrency": {
        "id": 2,
        "code": "USD",
        "name": "US Dollar",
        "sign": "$"
    },
    "targetCurrency": {
        "id": 3,
        "code": "UAH",
        "name": "Hryvnia",
        "sign": "₴"
    },
    "rate": 37.65
}
```
GET Exchange
```
{
    "baseCurrency": {
        "id": 2,
        "code": "USD",
        "name": "US Dollar",
        "sign": "$"
    },
    "targetCurrency": {
        "id": 3,
        "code": "UAH",
        "name": "Hryvnia",
        "sign": "₴"
    },
    "rate": 37.65,
    "amount": 2.99,
    "convertedAmount": 112.57
}
```
# Currency Exchanger

## Description
`Currency Exchanger` - It is Java Servlet APP, REST API for currency exchange.The main functions include managing currencies, setting exchange rates and calculating the amount of exchange at direct, reverse and cross rates.

## Technologies
The project is developed using Jakarta Servlets and is supported on a Tomcat 10 server.

## Settings and Launch
For Launch `currency-exchange-api` complete 3 steps:
1. Clone the repository.
2. Collect war artefact, using command `mvn package`.
3. Запустите приложение, используя команду `java -jar [имя_собранного_артефакта].jar`.

## Использование API
- `GET /currencies` - получение списка доступных валют.
- `GET /exchange` - выполнение операции обмена валют. Для использования необходимо указать параметры в запросе.

## Лицензия
Этот проект лицензирован под лицензией MIT.

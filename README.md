# Currency Exchanger

## Description
`Currency Exchanger` - It is Java Servlet App, REST API for currency exchange.The main functions include managing currencies, setting exchange rates and calculating the amount of exchange at direct, reverse and cross rates.

## Technologies
The project is developed using Jakarta Servlets and is supported on a Tomcat 10 server.

## Settings and Launch
For Launch `Currency Exchanger` complete 3 steps:
1. Clone the repository.
2. Collect war artefact, using command `mvn clean package`.
3. Launch the application using tomcat in intellij idea or copy war file to tomcat in web app and in tomcat/bin launch startup script.

## Using API
- `GET /currencies` - получение списка доступных валют.
- `GET /exchange` - выполнение операции обмена валют. Для использования необходимо указать параметры в запросе.
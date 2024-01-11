create table Currencies (
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    Code VARCHAR UNIQUE ,
    FullName VARCHAR,
    Sign VARCHAR
)

INSERT INTO currencies (ID, Code, FullName, Sign)
VALUES (2, 'USD', 'US Dollar', '$');

INSERT INTO currencies (ID, Code, FullName, Sign)
VALUES (3, 'UAH', 'Hryvnia', '₴');

INSERT INTO currencies (ID, Code, FullName, Sign)
VALUES (4, 'EUR', 'Euro', '€');

INSERT INTO currencies (ID, Code, FullName, Sign)
VALUES (5, 'GBP', 'Pound Sterling', '£');

INSERT INTO currencies (ID, Code, FullName, Sign)
VALUES (6, 'PLN', 'Zloty', 'Zł');

INSERT INTO currencies (ID, Code, FullName, Sign)
VALUES (7, 'CZK', 'Czech Koruna', 'Kč');

create table ExchangeRates (
                               ID INTEGER PRIMARY KEY AUTOINCREMENT ,
                               BaseCurrencyId integer ,
                               TargetCurrencyId integer,
                               Rate DECIMAL(6),
                               foreign key (BaseCurrencyId) references currencies(ID),
                               foreign key (TargetCurrencyId) references currencies(ID)
)
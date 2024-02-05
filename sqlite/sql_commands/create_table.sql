create table Currencies (
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    Code VARCHAR UNIQUE ,
    FullName VARCHAR,
    Sign VARCHAR
)

INSERT INTO Currencies (ID, Code, FullName, Sign) VALUES (2, 'USD', 'US Dollar', '$');
INSERT INTO Currencies (ID, Code, FullName, Sign) VALUES (3, 'UAH', 'Hryvnia', '₴');
INSERT INTO Currencies (ID, Code, FullName, Sign) VALUES (4, 'EUR', 'Euro', '€');
INSERT INTO Currencies (ID, Code, FullName, Sign) VALUES (5, 'GBP', 'Pound Sterling', '£');
INSERT INTO Currencies (ID, Code, FullName, Sign) VALUES (6, 'PLN', 'Zloty', 'Zł');
INSERT INTO Currencies (ID, Code, FullName, Sign) VALUES (7, 'CZK', 'Czech Koruna', 'Kč');
INSERT INTO Currencies (ID, Code, FullName, Sign) VALUES (8, 'AUD', 'AUSTRALIAN DOLLAR', 'A$');
INSERT INTO Currencies (ID, Code, FullName, Sign) VALUES (12, 'EGP', 'Egyptian Pound', 'E£');
INSERT INTO Currencies (ID, Code, FullName, Sign) VALUES (13, 'BRL', 'Brazilian Real', 'R$');
INSERT INTO Currencies (ID, Code, FullName, Sign) VALUES (14, 'INR', 'Indian Rupee', '₹');


create table ExchangeRates (
                               ID INTEGER PRIMARY KEY AUTOINCREMENT ,
                               BaseCurrencyId integer ,
                               TargetCurrencyId integer,
                               Rate DECIMAL(6),
                               foreign key (BaseCurrencyId) references currencies(ID),
                               foreign key (TargetCurrencyId) references currencies(ID)
);

INSERT INTO ExchangeRates (ID, BaseCurrencyId, TargetCurrencyId, Rate) VALUES (1, 2, 4, 0.93);
INSERT INTO ExchangeRates (ID, BaseCurrencyId, TargetCurrencyId, Rate) VALUES (2, 4, 2, 1.08);
INSERT INTO ExchangeRates (ID, BaseCurrencyId, TargetCurrencyId, Rate) VALUES (3, 2, 3, 37.65);
INSERT INTO ExchangeRates (ID, BaseCurrencyId, TargetCurrencyId, Rate) VALUES (4, 4, 3, 40.78);
INSERT INTO ExchangeRates (ID, BaseCurrencyId, TargetCurrencyId, Rate) VALUES (5, 7, 3, 1.65);
INSERT INTO ExchangeRates (ID, BaseCurrencyId, TargetCurrencyId, Rate) VALUES (6, 3, 2, 0.027);
INSERT INTO ExchangeRates (ID, BaseCurrencyId, TargetCurrencyId, Rate) VALUES (7, 2, 6, 4.02);
INSERT INTO ExchangeRates (ID, BaseCurrencyId, TargetCurrencyId, Rate) VALUES (8, 2, 7, 22.78);
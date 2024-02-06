CREATE TABLE IF NOT EXISTS Currencies (
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    Code VARCHAR UNIQUE,
    FullName VARCHAR,
    Sign VARCHAR
);
INSERT INTO Currencies (ID, Code, FullName, Sign) VALUES (2, 'USD', 'US Dollar', '$');
INSERT INTO Currencies (ID, Code, FullName, Sign) VALUES (3, 'UAH', 'Hryvnia', '₴');
INSERT INTO Currencies (ID, Code, FullName, Sign) VALUES (4, 'EUR', 'Euro', '€');

CREATE TABLE IF NOT EXISTS ExchangeRates (
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    BaseCurrencyId INTEGER,
    TargetCurrencyId INTEGER,
    Rate DECIMAL(6),
    FOREIGN KEY (BaseCurrencyId) REFERENCES currencies(ID),
    FOREIGN KEY (TargetCurrencyId) REFERENCES currencies(ID)
);
INSERT INTO ExchangeRates (ID, BaseCurrencyId, TargetCurrencyId, Rate) VALUES (1, 2, 3, 37.65);
INSERT INTO ExchangeRates (ID, BaseCurrencyId, TargetCurrencyId, Rate) VALUES (2, 2, 4, 0.93);
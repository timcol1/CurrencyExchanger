PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS Currencies (
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    Code VARCHAR UNIQUE,
    FullName VARCHAR,
    Sign VARCHAR
);

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


CREATE TABLE IF NOT EXISTS ExchangeRates (
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    BaseCurrencyId INTEGER NOT NULL,
    TargetCurrencyId INTEGER NOT NULL,
    Rate DECIMAL(6),
    FOREIGN KEY (BaseCurrencyId) REFERENCES currencies(ID),
    FOREIGN KEY (TargetCurrencyId) REFERENCES currencies(ID),
    CONSTRAINT unique_currency_pair UNIQUE (BaseCurrencyId, TargetCurrencyId)
);

INSERT INTO ExchangeRates (ID, BaseCurrencyId, TargetCurrencyId, Rate) VALUES (1, 2, 4, 0.93);
INSERT INTO ExchangeRates (ID, BaseCurrencyId, TargetCurrencyId, Rate) VALUES (2, 4, 2, 1.08);
INSERT INTO ExchangeRates (ID, BaseCurrencyId, TargetCurrencyId, Rate) VALUES (3, 2, 3, 37.65);
INSERT INTO ExchangeRates (ID, BaseCurrencyId, TargetCurrencyId, Rate) VALUES (4, 4, 3, 40.78);
INSERT INTO ExchangeRates (ID, BaseCurrencyId, TargetCurrencyId, Rate) VALUES (5, 7, 3, 1.65);
INSERT INTO ExchangeRates (ID, BaseCurrencyId, TargetCurrencyId, Rate) VALUES (6, 3, 2, 0.027);
INSERT INTO ExchangeRates (ID, BaseCurrencyId, TargetCurrencyId, Rate) VALUES (7, 2, 6, 4.02);
INSERT INTO ExchangeRates (ID, BaseCurrencyId, TargetCurrencyId, Rate) VALUES (8, 2, 7, 22.78);
package avlyakulov.timur.dao;

import avlyakulov.timur.connection.DataSourceSimpleConnection;
import avlyakulov.timur.model.Currency;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
class CurrencyDaoTest {

    private Connection getConnection() throws SQLException {
        return DataSourceSimpleConnection.getConnection();
    }

    @BeforeEach
    void setUp() throws URISyntaxException, IOException, SQLException, ClassNotFoundException {
        URL url = CurrencyDaoTest.class.getClassLoader().getResource("create_table_test.sql");
        List<String> strings = Files.readAllLines(Paths.get(url.toURI()));
        //здесь мы все строчки собираем в 1 строку
        String sql = strings.stream().collect(Collectors.joining());
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
        log.info("Sql query to create tables was completed");
    }

    @AfterEach()
    void cleanUp() throws SQLException, ClassNotFoundException {
        String sql = "DROP TABLE IF EXISTS Currencies; DROP TABLE IF EXISTS ExchangeRates;";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
        log.info("Sql query to drop tables was completed");
    }

    @Test
    void test1() throws SQLException {
        List<Currency> currencies = new CurrencyDaoImpl().findAll();
        int a = 123;
    }

//    @Test
//    void test2() {
//        System.out.println("Test 2");
//    }
//
//    @Test
//    void test3() {
//        System.out.println("Test 3");
//    }
}
package avlyakulov.timur.dao;

import avlyakulov.timur.connection.DataSourceSimpleConnection;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class DBState {
    public static void createDB() throws SQLException, IOException, URISyntaxException {
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

    public static void cleanDB() throws SQLException {
        String sql = "DROP TABLE IF EXISTS Currencies; DROP TABLE IF EXISTS ExchangeRates;";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
        log.info("Sql query to drop tables was completed");
    }

    private static Connection getConnection() throws SQLException {
        return DataSourceSimpleConnection.getConnection();
    }
}
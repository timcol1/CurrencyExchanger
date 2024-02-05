package avlyakulov.timur.dao;

import avlyakulov.timur.connection.DataSourceTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class CurrencyDaoTest {

    @BeforeAll
    public static void startUp() throws URISyntaxException, IOException, SQLException {
        URL url = CurrencyDaoTest.class.getClassLoader().getResource("create_table_test.sql");
        List<String> strings = Files.readAllLines(Paths.get(url.toURI()));
        //здесь мы все строчки собираем в 1 строку
        String sql = strings.stream().collect(Collectors.joining());
        try (Connection connection = DataSourceTest.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        }
    }

    @Test
    void test1() {
        System.out.println("Test 1");
    }

    @Test
    void test2() {
        System.out.println("Test 2");
    }

    @Test
    void test3() {
        System.out.println("Test 3");
    }
}
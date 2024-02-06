package avlyakulov.timur.connection;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class DataSourceSimpleConnection {

    @SneakyThrows
    public static Connection getConnection() throws SQLException {
        Class.forName("org.sqlite.JDBC");
        URL resource = DataSourceHikariPool.class.getClassLoader().getResource("currencies_test.db");
        String path = null;
        if (resource != null) {
            try {
                path = new File(resource.toURI()).getAbsolutePath();
            } catch (URISyntaxException e) {
                log.error("Failed to connect to db");
                throw new RuntimeException(e);
            }
        }
        return DriverManager.getConnection(String.format("jdbc:sqlite:%s", path));
    }
}

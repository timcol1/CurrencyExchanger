package avlyakulov.timur.connection;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
public class ConnectionDB {

    public static Connection getConnection() {
        try {
            return DataSourceHikariPool.getConnection();
        } catch (SQLException e) {
            log.error("Failed to connect to DB");
            throw new RuntimeException(e);
        }
    }
}
package avlyakulov.timur.connection;

import avlyakulov.timur.dao.DeploymentEnvironment;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
public class ConnectionDB {

    public static Connection getConnection(DeploymentEnvironment deploymentEnvironment) {
        switch (deploymentEnvironment) {
            case PROD -> {
                try {
                    return DataSourceHikariPool.getConnection();
                } catch (SQLException e) {
                    log.error("Failed to connect to DB");
                    throw new RuntimeException(e);
                }
            }
            case TEST -> {
                try {
                    return DataSourceSimpleConnectionTestDB.getConnection();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            default -> {
                log.error("Error in connection db no enum was switched");
                throw new RuntimeException("Something went wrong in ConnectionDB");
            }
        }
    }
}
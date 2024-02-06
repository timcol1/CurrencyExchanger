package avlyakulov.timur.connection;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionDB {
    public static Connection getConnection() throws SQLException {
        return DataSourceSimpleConnection.getConnection();
    }
}
package avlyakulov.timur.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {
    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;

    static {
        config.setDriverClassName("org.sqlite.JDBC");
        // Получение URL ресурса, связанного с классом
        String classResourcePath = DataSource.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        // Выделение пути к каталогу приложения из пути к классу
        String appPath = classResourcePath.substring(0, classResourcePath.indexOf("/target"));
        config.setJdbcUrl("jdbc:sqlite:" + appPath + "/sqlite/currencies.db");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    private DataSource() {
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
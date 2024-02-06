package avlyakulov.timur.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
public class DataSourceHikariPool {
    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;

    static {
        config.setDriverClassName("org.sqlite.JDBC");
        URL resource = DataSourceHikariPool.class.getClassLoader().getResource("currencies.db");
        String path = null;
        if (resource != null) {
            try {
                path = new File(resource.toURI()).getAbsolutePath();
            } catch (URISyntaxException e) {
                log.error("Failed to connect to db");
                throw new RuntimeException(e);
            }
        }
        config.setJdbcUrl(String.format("jdbc:sqlite:%s", path));
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    private DataSourceHikariPool() {
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
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
public class DataSourceTest {

    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;

    static {
        config.setDriverClassName("org.sqlite.JDBC");
        URL resource = DataSourceTest.class.getClassLoader().getResource("currencies_test.db");
        String path = null;
        if (resource != null) {
            try {
                path = new File(resource.toURI()).getAbsolutePath();
            } catch (URISyntaxException e) {
                log.error("Failed to connect to db in test folder");
                throw new RuntimeException(e);
            }
        }
        config.setJdbcUrl(String.format("jdbc:sqlite:%s", path));
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    private DataSourceTest() {
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}

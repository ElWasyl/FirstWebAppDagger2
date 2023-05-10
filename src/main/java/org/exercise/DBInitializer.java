package org.exercise;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class DBInitializer {
    public static void DBInit(){
        DataSource dataSource = createDataSource();
        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();
    }

    private static DataSource createDataSource() {
        Properties properties = new Properties();
        try {
            properties.load(Files.newInputStream(Paths.get("src/main/resources/flyway.conf")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL(properties.getProperty("flyway.url"));
        jdbcDataSource.setUser(properties.getProperty("flyway.user"));
        jdbcDataSource.setPassword(properties.getProperty("flyway.password"));
        return jdbcDataSource;
    }

}

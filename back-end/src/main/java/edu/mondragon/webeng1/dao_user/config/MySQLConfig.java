package edu.mondragon.webeng1.dao_user.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;

/**
 * Class to reuse repetitive MySQL tasks.
 * It is a singleton class so database.properties is only loaded once.
 * 
 * @author aperez
 */
public class MySQLConfig {
    /*
     * This is not the best policy to create connections, but is used to simplify
     * it.
     * More info at: https://www.baeldung.com/java-connection-pooling
     */

    private static MySQLConfig myconfig;

    private MysqlConnectionPoolDataSource dataSource;

    /**
     * Singleton pattern.
     * getInstance() must be static, otherwise it cannot be called (as constructor
     * is private).
     */
    public static MySQLConfig getInstance() {
        if (myconfig == null) {
            myconfig = new MySQLConfig();
        }
        return myconfig;
    }

    /**
     * Private constructor.
     * If someone wants the instance, it should call MySQLConfig.getInstance()
     * That way the constructor is called only once.
     */
    private MySQLConfig() {
        Properties prop = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            prop.load(classLoader.getResourceAsStream("db.properties"));
            String serverName = prop.getProperty("serverName");
            String port = prop.getProperty("port");
            String dataBaseName = prop.getProperty("dataBaseName");
            String url = prop.getProperty("url");
            String timezone = prop.getProperty("timezone");
            String username = prop.getProperty("username");
            String password = prop.getProperty("password");

            String connectionString =   url + 
                                        serverName + ":" + port + 
                                        "/" + dataBaseName +
                                        "?serverTimezone=" + timezone;

            // Create the connection pool
            dataSource = new MysqlConnectionPoolDataSource();
            dataSource.setURL(connectionString);
            dataSource.setUser(username);
            dataSource.setPassword(password);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get a new MySQL Connection from pool.
     */
    public Connection connect() {
        Connection connection = null;
        try {
            // Get connection from pool
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not get a connection from the pool");
        }
        System.out.println("Number of open connexions: "+ dataSource);
        return connection;
    }

    /**
     * Close a MySQL Connection.
     */
    public void disconnect(Connection connection, Statement statement) {
        try {
            if (statement != null) {
                statement.clearWarnings();
                statement.close();
            }

            if (connection != null) {
                connection.clearWarnings();
                /*
                As the connextion was obtained from a connection pool,
                close() does not actually close it but returns it to the pool 
                and makes it available for subsequent connection requests.
                https://dev.mysql.com/doc/connector-python/en/connector-python-api-mysqlconnection-close.html
                */
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error disconnecting");
        }
    }
}

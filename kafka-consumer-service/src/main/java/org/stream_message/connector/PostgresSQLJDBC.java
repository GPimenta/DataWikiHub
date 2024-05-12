package org.stream_message.connector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.Executors;


public class PostgresSQLJDBC {
    private static final Logger logger = LoggerFactory.getLogger(PostgresSQLJDBC.class.getName());

    final private String URL;
    final private String username;
    final private String password;

    public PostgresSQLJDBC(String host, String port, String database) {
        String basePath = "jdbc:postgresql:";
        this.URL = String.format("%s//%s:%s/%s", basePath, host, port, database);
        this.username = "postgres";
        this.password = "postgres";
    }

    public Connection getConnection () {
        try {
            Connection connection = DriverManager.getConnection(URL, username, password);
            connection.setNetworkTimeout(Executors.newSingleThreadExecutor(),5000);
            connection.setAutoCommit(false);
            if (connection.isValid(1000)) {
                logger.info("Success making connection to postgres");
                connection.setAutoCommit(true);
                return connection;
            } else {
                logger.error("Not successfully in making the connection to postgres");
                return null;
            }
        }catch (SQLException e) {
            logger.error("Error in making connection to postgres");
            return null;
        }
    }
}

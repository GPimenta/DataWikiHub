package org.stream_message.connector;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;


public class PostgresSQLJDBCTest {
    @Test
    public void getConnectionTest() throws SQLException {
        String host = "localhost";
        String port = "5432";
        String database = "WIKI_ARTICLES";
        PostgresSQLJDBC connector = new PostgresSQLJDBC(host, port, database);
        Connection connection = connector.getConnection();
        assert(!connection.isClosed());
        connection.close();
    }
}

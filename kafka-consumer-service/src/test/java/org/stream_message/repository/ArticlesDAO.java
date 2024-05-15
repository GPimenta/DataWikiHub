package org.stream_message.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stream_message.connector.PostgresSQLJDBC;
import org.stream_message.model.PageSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticlesDAO {

    private static final Logger logger = LoggerFactory.getLogger(ArticlesDAO.class.getName());
    private final PostgresSQLJDBC postgresSQLJDBC;
    private final String SELECT_ALL_ARTICLES = "SELECT * FROM ARTICLES;";
    private final String QUERY_id = "SELECT id, key, title, content, source, latest_version_timestamp FROM ARTICLES WHERE id = ?;";
    private final String QUERY_key = "SELECT id, key, title, content, source, latest_version_timestamp FROM ARTICLES WHERE key = ?;";
    private final String QUERY_content = "SELECT id, key, title, content, source, latest_version_timestamp FROM ARTICLES WHERE content = ?;";
    private final String INSERT_ARTICLE = "INSERT INTO ARTICLES (key, title, content, source, latest_version_timestamp) WHERE VALUES (?, ?, ?, ?, ?);";
    private final String UPDATE_ARTICLE = "UPDATE ARTICLES SET (key, title, content, source, latest_version_timestamp) WHERE VALUES (?, ?, ?, ?, ?);";


    public ArticlesDAO(PostgresSQLJDBC postgresSQLJDBC) {
        this.postgresSQLJDBC = postgresSQLJDBC;
    }


    public void getAllArticles() {
        try {
            PreparedStatement preparedStatement = postgresSQLJDBC.getConnection().prepareStatement(SELECT_ALL_ARTICLES);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                PageSource pageSource = resultSet.getInt()
            }

        } catch (SQLException e) {
            logger.error("Error using SELECT * FROM ARTICLES query");
            throw new RuntimeException(e);
        }

    }

}

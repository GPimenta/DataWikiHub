package org.stream_message.repository;

import org.apache.zookeeper.Op;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stream_message.connector.PostgresSQLJDBC;
import org.stream_message.model.PageSourcePostgres;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArticlesDAO {

    private static final Logger logger = LoggerFactory.getLogger(ArticlesDAO.class.getName());
    private final PostgresSQLJDBC postgresSQLJDBC;
    private final String SELECT_ALL_ARTICLES = "SELECT * FROM ARTICLES;";
    private final String QUERY_id = "SELECT id, key, title, content, source, latest_version_timestamp FROM ARTICLES WHERE id = ?;";
    private final String QUERY_key = "SELECT id, key, title, content, source, latest_version_timestamp FROM ARTICLES WHERE key = ?;";
    private final String INSERT_ARTICLE = "INSERT INTO ARTICLES (key, title, content, source, latest_version_timestamp) WHERE VALUES (?, ?, ?, ?, ?);";
    private final String UPDATE_ARTICLE = "UPDATE ARTICLES SET (key, title, content, source, latest_version_timestamp) WHERE VALUES (?, ?, ?, ?, ?);";

    public ArticlesDAO(PostgresSQLJDBC postgresSQLJDBC) {
        this.postgresSQLJDBC = postgresSQLJDBC;
    }

    public Optional<List<PageSourcePostgres>> getAllArticles() {
        try (PreparedStatement preparedStatement = postgresSQLJDBC.getConnection().prepareStatement(SELECT_ALL_ARTICLES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<PageSourcePostgres> pageSourcePostgresList = new ArrayList<>();

            while (resultSet.next()) {
                PageSourcePostgres pageSource = new PageSourcePostgres.Builder()
                        .id(resultSet.getInt("id"))
                        .key(resultSet.getString("key"))
                        .title(resultSet.getString("title"))
                        .latest(resultSet.getTimestamp("latest_version_timestamp").toLocalDateTime())
                        .contentModel(resultSet.getString("content_model"))
                        .source(resultSet.getString("source"))
                        .redirectTarget(resultSet.getString("redirect_target"))
                        .build();
                pageSourcePostgresList.add(pageSource);
            }
            return Optional.of(pageSourcePostgresList);
        } catch (SQLException e) {
            logger.error("Error using SELECT * FROM ARTICLES query");
            throw new RuntimeException(e);
        }
    }

    public Optional<PageSourcePostgres> getById(int id) {
        try (PreparedStatement preparedStatement = postgresSQLJDBC.getConnection().prepareStatement(QUERY_id)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                PageSourcePostgres pageSource = new PageSourcePostgres.Builder()
                        .id(resultSet.getInt("id"))
                        .key(resultSet.getString("key"))
                        .title(resultSet.getString("title"))
                        .latest(resultSet.getTimestamp("latest_version_timestamp").toLocalDateTime())
                        .contentModel(resultSet.getString("content_model"))
                        .source(resultSet.getString("source"))
                        .redirectTarget(resultSet.getString("redirect_target"))
                        .build();
                return Optional.of(pageSource);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.error("Error using QUERY_id");
            throw new RuntimeException(e);
        }
    }

    public Optional<PageSourcePostgres> getByKey(String key) {
        try (PreparedStatement preparedStatement = postgresSQLJDBC.getConnection().prepareStatement(QUERY_key)) {
            preparedStatement.setString(1, key);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                PageSourcePostgres pageSource = new PageSourcePostgres.Builder()
                        .id(resultSet.getInt("id"))
                        .key(resultSet.getString("key"))
                        .title(resultSet.getString("title"))
                        .latest(resultSet.getTimestamp("latest_version_timestamp").toLocalDateTime())
                        .contentModel(resultSet.getString("content_model"))
                        .source(resultSet.getString("source"))
                        .redirectTarget(resultSet.getString("redirect_target"))
                        .build();
                return Optional.of(pageSource);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.error("Error using QUERY_key");
            throw new RuntimeException();
        }
    }
// "INSERT INTO ARTICLES (key, title, content, source, latest_version_timestamp) WHERE VALUES (?, ?, ?, ?, ?);";
    public boolean setArticle(PageSourcePostgres pageSourcePostgres) {
        try {
            if (getByKey(pageSourcePostgres.getKey()).isPresent()) {
                logger.info("Article already exists on postgres DB");
                return true;
            }
            logger.info("Article is new in the DB");
            try (PreparedStatement preparedStatement = postgresSQLJDBC.getConnection().prepareStatement(INSERT_ARTICLE)) {
                preparedStatement.setString(1, pageSourcePostgres.getKey());
                preparedStatement.setString(2, pageSourcePostgres.getTitle());
                preparedStatement.setString(3, pageSourcePostgres.getContentModel());
                preparedStatement.setString(4, pageSourcePostgres.getSource());
                preparedStatement.setTimestamp(5, Timestamp.valueOf(pageSourcePostgres.getLatest()));

                return preparedStatement.execute();
            }
        } catch (SQLException e) {
            logger.error("Error inserting the article in setArticle");
            throw new RuntimeException(e);
        }
    }

    // "UPDATE ARTICLES SET (key, title, content, source, latest_version_timestamp) WHERE VALUES (?, ?, ?, ?, ?);";
    public boolean updateArticle(PageSourcePostgres pageSourcePostgres) {
        try {
            if (getByKey(pageSourcePostgres.getKey()).isEmpty()) {
                logger.info("Article does not exist, as such no update was made");
                return false;
            }
            logger.info("Article found");
            try (PreparedStatement preparedStatement = postgresSQLJDBC.getConnection().prepareStatement(UPDATE_ARTICLE)) {
                preparedStatement.setString(1, pageSourcePostgres.getKey());
                preparedStatement.setString(2, pageSourcePostgres.getTitle());
                preparedStatement.setString(3, pageSourcePostgres.getContentModel());
                preparedStatement.setString(4, pageSourcePostgres.getSource());
                preparedStatement.setTimestamp(5, Timestamp.valueOf(pageSourcePostgres.getLatest()));

                return preparedStatement.execute();
            }
        } catch (SQLException e) {
            logger.error("Error updating article");
            throw new RuntimeException(e);
        }
    }
}

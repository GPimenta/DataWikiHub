package org.stream_message.repository;

import org.checkerframework.checker.nullness.Opt;
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
    private final String QUERY_id = "SELECT id, key, title, content_model, source, latest_version_timestamp, redirect_target FROM ARTICLES WHERE id = ?;";
    private final String QUERY_key = "SELECT id, key, title, content_model, source, latest_version_timestamp, redirect_target FROM ARTICLES WHERE key = ?;";
    private final String INSERT_ARTICLE = "INSERT INTO ARTICLES (key, title, content_model, source, latest_version_timestamp, redirect_target) VALUES (?, ?, ?, ?, ?, ?);";
//    private final String UPDATE_ARTICLE = "UPDATE ARTICLES SET title = ?, content_model = ?, source = ?, latest_version_timestamp = ? content_model, source, latest_version_timestamp, redirect_target WHERE key = ? ;";
    private final String UPDATE_ARTICLE = "UPDATE ARTICLES SET title = ?, content_model = ?, source = ?, latest_version_timestamp = ?, redirect_target = ? WHERE key = ?;";
    private final String DELETE_ARTICLE = "DELETE FROM ARTICLES WHERE key = ?;";

    public ArticlesDAO(PostgresSQLJDBC postgresSQLJDBC) {
        this.postgresSQLJDBC = postgresSQLJDBC;
    }

    public List<Optional<PageSourcePostgres>> getAllArticles() {
        try (PreparedStatement preparedStatement = postgresSQLJDBC.getConnection().prepareStatement(SELECT_ALL_ARTICLES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Optional<PageSourcePostgres>> pageSourcePostgresList = new ArrayList<>();

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
                pageSourcePostgresList.add(Optional.of(pageSource));
            }
            return pageSourcePostgresList;
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
            logger.error("Error using QUERY_key = {0}", e);
            throw new RuntimeException();
        }
    }
// "INSERT INTO ARTICLES (key, title, content, source, latest_version_timestamp) WHERE VALUES (?, ?, ?, ?, ?);";
    public Optional<PageSourcePostgres> setArticle(PageSourcePostgres pageSourcePostgres) {
        try {
            Optional<PageSourcePostgres> articleKey = getByKey(pageSourcePostgres.getKey());
            if (articleKey.isPresent()) {
                logger.info("Article already exists on postgres DB");
                return articleKey;
            }
            logger.info("Article is new in the DB");
            logger.debug("Latest Version Timestamp: " + pageSourcePostgres.getLatest());
            logger.debug("Latest Version Timestamp: " + pageSourcePostgres.getLatest());

            try (PreparedStatement preparedStatement = postgresSQLJDBC.getConnection().prepareStatement(INSERT_ARTICLE)) {
                preparedStatement.setString(1, pageSourcePostgres.getKey());
                preparedStatement.setString(2, pageSourcePostgres.getTitle());
                preparedStatement.setString(3, pageSourcePostgres.getContentModel());
                preparedStatement.setString(4, pageSourcePostgres.getSource());
                preparedStatement.setTimestamp(5, Timestamp.valueOf(pageSourcePostgres.getLatest()));
                preparedStatement.setString(6, pageSourcePostgres.getRedirectTarget());

                return preparedStatement.executeUpdate() == 1 ? getByKey(pageSourcePostgres.getKey()) : Optional.empty();

            }
        } catch (SQLException e) {
            logger.error("Error inserting the article in setArticle");
            throw new RuntimeException(e);
        }
    }

    // "UPDATE ARTICLES SET (key, title, content, source, latest_version_timestamp) WHERE VALUES (?, ?, ?, ?, ?);";
    public Optional<PageSourcePostgres> updateArticle(PageSourcePostgres pageSourcePostgres) {
        try {
            if (getByKey(pageSourcePostgres.getKey()).isEmpty()) {
                logger.info("Article does not exist, as such no update was made");
                return Optional.empty();
            }
            logger.info("Article found to update");
            try (PreparedStatement preparedStatement = postgresSQLJDBC.getConnection().prepareStatement(UPDATE_ARTICLE)) {
                preparedStatement.setString(1, pageSourcePostgres.getKey());
                preparedStatement.setString(2, pageSourcePostgres.getTitle());
                preparedStatement.setString(3, pageSourcePostgres.getContentModel());
                preparedStatement.setString(4, pageSourcePostgres.getSource());
                preparedStatement.setTimestamp(5, Timestamp.valueOf(pageSourcePostgres.getLatest()));
                preparedStatement.setString(6, pageSourcePostgres.getRedirectTarget());

                return preparedStatement.executeUpdate() == 1 ? getByKey(pageSourcePostgres.getKey()) : Optional.empty();
            }
        } catch (SQLException e) {
            logger.error("Error updating article = {0}", e);
            throw new RuntimeException(e);
        }
    }

    public boolean deleteArticle(PageSourcePostgres pageSourcePostgres) {
        try {
            if (getByKey(pageSourcePostgres.getKey()).isEmpty()) {
                logger.info("Article does not exist, as such no delete was made");
                return false;
            }
            logger.info("Article found");
            try (PreparedStatement preparedStatement = postgresSQLJDBC.getConnection().prepareStatement(DELETE_ARTICLE)) {
                preparedStatement.setString(1, pageSourcePostgres.getKey());

                return preparedStatement.executeUpdate() == 1;
            }
        } catch (SQLException e) {
            logger.error("Error deleting article");
            throw new RuntimeException(e);
        }
    }
}

package org.stream_message.controller;

import org.junit.jupiter.api.*;
import org.stream_message.connector.PostgresSQLJDBC;
import org.stream_message.model.PageSourcePostgres;
import org.stream_message.repository.ArticlesDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ArticlesControllerTest {

    private final String host = "localhost";
    private final String port = "5432";
    private final String database = "WIKI_ARTICLES";
    private PostgresSQLJDBC postgresSQLJDBC = new PostgresSQLJDBC(host, port, database);
    private ArticlesDAO articlesDAO = new ArticlesDAO(postgresSQLJDBC);
    private ArticlesController articlesController = new ArticlesController(articlesDAO);

    private PageSourcePostgres testArticle1;
    private PageSourcePostgres testArticle2;
    private PageSourcePostgres testArticle3;

    @BeforeAll
    public void setupAll() {
        testArticle1 = new PageSourcePostgres.Builder()
                .id(1)
                .key("testingKey1")
                .title("testingTitle1")
                .latest(LocalDateTime.now())
                .contentModel("testingContent1")
                .source("testingSource1")
                .redirectTarget("redirectTarget1")
                .build();

        testArticle2 = new PageSourcePostgres.Builder()
                .key("testingKey2")
                .title("testingTitle2")
                .latest(LocalDateTime.now())
                .contentModel("testingContent2")
                .source("testingSource2")
                .redirectTarget("redirectTarget2")
                .build();

        testArticle3 = new PageSourcePostgres.Builder()
                .key("testingKey3")
                .title("testingTitle3")
                .latest(LocalDateTime.now())
                .contentModel("testingContent3")
                .source("testingSource3")
                .redirectTarget("redirectTarget3")
                .build();
    }

    @BeforeEach
    public void setup() {
        try(Connection connection = postgresSQLJDBC.getConnection()) {
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM ARTICLES");
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            fail("Setup failed: Unable to clear ARTICLES table");
        }
        assertTrue(
                articlesDAO.setArticle(testArticle1).isPresent()
                == articlesDAO.setArticle(testArticle2).isPresent()
                == articlesDAO.setArticle(testArticle3).isPresent(),
                "The article should be inserted successfully");
    }

    @AfterEach
    public void teardown() {
        try(Connection connection = postgresSQLJDBC.getConnection()) {
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM ARTICLES");
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            fail("Setup failed: Unable to clear ARTICLES table");
        }
    }

}

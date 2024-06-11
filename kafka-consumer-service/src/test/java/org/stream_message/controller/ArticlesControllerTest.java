package org.stream_message.controller;

import org.junit.jupiter.api.*;
import org.stream_message.connector.PostgresSQLJDBC;
import org.stream_message.model.PageSourcePostgres;
import org.stream_message.repository.ArticlesDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    public void getAllArticlesTest() {
        List<PageSourcePostgres> allArticles = articlesController.getAllArticles();
        PageSourcePostgres pageSourcePostgres1 = allArticles.get(0);
        PageSourcePostgres pageSourcePostgres2 = allArticles.get(1);
        PageSourcePostgres pageSourcePostgres3 = allArticles.get(2);

        assertEquals(testArticle1.getKey(), pageSourcePostgres1.getKey(), "The keys should match");
        assertEquals(testArticle1.getTitle(), pageSourcePostgres1.getTitle(), "The titles should match");
        assertEquals(testArticle1.getLatest(), pageSourcePostgres1.getLatest(), "The latest timestamps should match");
        assertEquals(testArticle1.getContentModel(), pageSourcePostgres1.getContentModel(), "The content models should match");
        assertEquals(testArticle1.getSource(), pageSourcePostgres1.getSource(), "The sources should match");
        assertEquals(testArticle1.getRedirectTarget(), pageSourcePostgres1.getRedirectTarget(), "The redirect targets should match");

        assertEquals(testArticle2.getKey(), pageSourcePostgres2.getKey(), "The keys should match");
        assertEquals(testArticle2.getTitle(), pageSourcePostgres2.getTitle(), "The titles should match");
        assertEquals(testArticle2.getLatest(), pageSourcePostgres2.getLatest(), "The latest timestamps should match");
        assertEquals(testArticle2.getContentModel(), pageSourcePostgres2.getContentModel(), "The content models should match");
        assertEquals(testArticle2.getSource(), pageSourcePostgres2.getSource(), "The sources should match");
        assertEquals(testArticle2.getRedirectTarget(), pageSourcePostgres2.getRedirectTarget(), "The redirect targets should match");

        assertEquals(testArticle3.getKey(), pageSourcePostgres3.getKey(), "The keys should match");
        assertEquals(testArticle3.getTitle(), pageSourcePostgres3.getTitle(), "The titles should match");
        assertEquals(testArticle3.getLatest(), pageSourcePostgres3.getLatest(), "The latest timestamps should match");
        assertEquals(testArticle3.getContentModel(), pageSourcePostgres3.getContentModel(), "The content models should match");
        assertEquals(testArticle3.getSource(), pageSourcePostgres3.getSource(), "The sources should match");
        assertEquals(testArticle3.getRedirectTarget(), pageSourcePostgres3.getRedirectTarget(), "The redirect targets should match");
    }

    @Test
    public void getByIdTest() {
        PageSourcePostgres articleByKey = articlesController.getByKey(testArticle1.getKey());
        PageSourcePostgres retrievedArticle = articlesController.getById(articleByKey.getId());

        assertEquals(testArticle1.getKey(), retrievedArticle.getKey(), "The keys should match");
        assertEquals(testArticle1.getTitle(), retrievedArticle.getTitle(), "The titles should match");
        assertEquals(testArticle1.getLatest(), retrievedArticle.getLatest(), "The latest timestamps should match");
        assertEquals(testArticle1.getContentModel(), retrievedArticle.getContentModel(), "The content models should match");
        assertEquals(testArticle1.getSource(), retrievedArticle.getSource(), "The sources should match");
        assertEquals(testArticle1.getRedirectTarget(), retrievedArticle.getRedirectTarget(), "The redirect targets should match");
    }

    @Test
    public void getByKeyTest() {
        PageSourcePostgres articleByKey = articlesController.getByKey(testArticle1.getKey());

        assertEquals(testArticle1.getKey(), articleByKey.getKey(), "The keys should match");
        assertEquals(testArticle1.getTitle(), articleByKey.getTitle(), "The titles should match");
        assertEquals(testArticle1.getLatest(), articleByKey.getLatest(), "The latest timestamps should match");
        assertEquals(testArticle1.getContentModel(), articleByKey.getContentModel(), "The content models should match");
        assertEquals(testArticle1.getSource(), articleByKey.getSource(), "The sources should match");
        assertEquals(testArticle1.getRedirectTarget(), articleByKey.getRedirectTarget(), "The redirect targets should match");

    }

    @Test
    public void setArticleTest() {
        PageSourcePostgres pageSourcePostgres = new PageSourcePostgres.Builder()
                .key("testingKeySetArticle")
                .title("testingTitleSetArticle")
                .latest(LocalDateTime.now())
                .contentModel("testingContentSetArticle")
                .source("testingSourceSetArticle")
                .redirectTarget("redirectTargetTestSetArticle")
                .build();

        PageSourcePostgres pageSourcePostgres1 = articlesController.setArticle(pageSourcePostgres);
        PageSourcePostgres retrievedArticle = articlesController.getByKey(pageSourcePostgres1.getKey());

        assertEquals(pageSourcePostgres.getKey(), retrievedArticle.getKey(), "The keys should match");
        assertEquals(pageSourcePostgres.getTitle(), retrievedArticle.getTitle(), "The titles should match");
        assertEquals(pageSourcePostgres.getLatest(), retrievedArticle.getLatest(), "The latest timestamps should match");
        assertEquals(pageSourcePostgres.getContentModel(), retrievedArticle.getContentModel(), "The content models should match");
        assertEquals(pageSourcePostgres.getSource(), retrievedArticle.getSource(), "The sources should match");
        assertEquals(pageSourcePostgres.getRedirectTarget(), retrievedArticle.getRedirectTarget(), "The redirect targets should match");
    }

    @Test
    public void updateArticleTest() {
        PageSourcePostgres pageSourcePostgres = new PageSourcePostgres.Builder()
                .key("testingKey2")
                .title("testingTitleSetArticle")
                .latest(LocalDateTime.now())
                .contentModel("testingContentSetArticle")
                .source("testingSourceSetArticle")
                .redirectTarget("redirectTargetTestSetArticle")
                .build();

        PageSourcePostgres pageSourcePostgres1 = articlesController.updateArticle(pageSourcePostgres);
        PageSourcePostgres retrievedArticle = articlesController.getByKey(pageSourcePostgres1.getKey());

        assertEquals(pageSourcePostgres.getKey(), retrievedArticle.getKey(), "The keys should match");
        assertEquals(pageSourcePostgres.getTitle(), retrievedArticle.getTitle(), "The titles should match");
        assertEquals(pageSourcePostgres.getLatest(), retrievedArticle.getLatest(), "The latest timestamps should match");
        assertEquals(pageSourcePostgres.getContentModel(), retrievedArticle.getContentModel(), "The content models should match");
        assertEquals(pageSourcePostgres.getSource(), retrievedArticle.getSource(), "The sources should match");
        assertEquals(pageSourcePostgres.getRedirectTarget(), retrievedArticle.getRedirectTarget(), "The redirect targets should match");
    }

    @Test
    public void deleteArticleTest() {
        PageSourcePostgres pageSourcePostgres = new PageSourcePostgres.Builder()
                .key("testingKeySetArticle")
                .title("testingTitleSetArticle")
                .latest(LocalDateTime.now())
                .contentModel("testingContentSetArticle")
                .source("testingSourceSetArticle")
                .redirectTarget("redirectTargetTestSetArticle")
                .build();


        articlesController.setArticle(pageSourcePostgres);
        assertTrue(articlesController.deleteArticle(pageSourcePostgres));

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class,
                () -> articlesController.getByKey(pageSourcePostgres.getKey()),
                "Unable to get articles by key" );

        assertEquals("Unable to get articles by key", thrown.getMessage());
    }

}

package org.stream_message.repository;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stream_message.connector.PostgresSQLJDBC;
import org.stream_message.model.PageSourcePostgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ArticlesDAOTest {
    private static final Logger logger = LoggerFactory.getLogger(ArticlesDAOTest.class.getName());

    private final String host = "localhost";
    private final String port = "5432";
    private final String database = "WIKI_ARTICLES";
    private final PostgresSQLJDBC postgresSQLJDBC = new PostgresSQLJDBC(host, port, database);
    private final ArticlesDAO articlesDAO = new ArticlesDAO(postgresSQLJDBC);
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
    public void getAllArticles() {
        List<Optional<PageSourcePostgres>> allArticles = articlesDAO.getAllArticles();
        assertTrue(allArticles.stream().map(Optional::isPresent).reduce(true, (subtotal, element) -> subtotal && element), "All articles are present in the list");

        Optional<PageSourcePostgres> retrievedArticle1 = allArticles.stream().filter(articlesDAO -> Objects.equals(articlesDAO.get().getKey(), testArticle1.getKey())).findFirst().get();
        Optional<PageSourcePostgres> retrievedArticle2 = allArticles.stream().filter(articlesDAO -> Objects.equals(articlesDAO.get().getKey(), testArticle2.getKey())).findFirst().get();
        Optional<PageSourcePostgres> retrievedArticle3 = allArticles.stream().filter(articlesDAO -> Objects.equals(articlesDAO.get().getKey(), testArticle3.getKey())).findFirst().get();

        assertTrue(retrievedArticle1.isPresent(), "The article should be present in the database");

        assertEquals(testArticle1.getKey(), retrievedArticle1.get().getKey(), "The keys should match");
        assertEquals(testArticle1.getTitle(), retrievedArticle1.get().getTitle(), "The titles should match");
        assertEquals(testArticle1.getLatest(), retrievedArticle1.get().getLatest(), "The latest timestamps should match");
        assertEquals(testArticle1.getContentModel(), retrievedArticle1.get().getContentModel(), "The content models should match");
        assertEquals(testArticle1.getSource(), retrievedArticle1.get().getSource(), "The sources should match");
        assertEquals(testArticle1.getRedirectTarget(), retrievedArticle1.get().getRedirectTarget(), "The redirect targets should match");

        assertTrue(retrievedArticle2.isPresent(), "The article should be present in the database");

        assertEquals(testArticle2.getKey(), retrievedArticle2.get().getKey(), "The keys should match");
        assertEquals(testArticle2.getTitle(), retrievedArticle2.get().getTitle(), "The titles should match");
        assertEquals(testArticle2.getLatest(), retrievedArticle2.get().getLatest(), "The latest timestamps should match");
        assertEquals(testArticle2.getContentModel(), retrievedArticle2.get().getContentModel(), "The content models should match");
        assertEquals(testArticle2.getSource(), retrievedArticle2.get().getSource(), "The sources should match");
        assertEquals(testArticle2.getRedirectTarget(), retrievedArticle2.get().getRedirectTarget(), "The redirect targets should match");

        assertTrue(retrievedArticle3.isPresent(), "The article should be present in the database");

        assertEquals(testArticle3.getKey(), retrievedArticle3.get().getKey(), "The keys should match");
        assertEquals(testArticle3.getTitle(), retrievedArticle3.get().getTitle(), "The titles should match");
        assertEquals(testArticle3.getLatest(), retrievedArticle3.get().getLatest(), "The latest timestamps should match");
        assertEquals(testArticle3.getContentModel(), retrievedArticle3.get().getContentModel(), "The content models should match");
        assertEquals(testArticle3.getSource(), retrievedArticle3.get().getSource(), "The sources should match");
        assertEquals(testArticle3.getRedirectTarget(), retrievedArticle3.get().getRedirectTarget(), "The redirect targets should match");
    }


    @Test
    public void getByKeyTest() {
        Optional<PageSourcePostgres> retrievedArticle = articlesDAO.getByKey(testArticle1.getKey());
        assertTrue(retrievedArticle.isPresent(), "The article should be present in the database");

        assertEquals(testArticle1.getKey(), retrievedArticle.get().getKey(), "The keys should match");
        assertEquals(testArticle1.getTitle(), retrievedArticle.get().getTitle(), "The titles should match");
        assertEquals(testArticle1.getLatest(), retrievedArticle.get().getLatest(), "The latest timestamps should match");
        assertEquals(testArticle1.getContentModel(), retrievedArticle.get().getContentModel(), "The content models should match");
        assertEquals(testArticle1.getSource(), retrievedArticle.get().getSource(), "The sources should match");
        assertEquals(testArticle1.getRedirectTarget(), retrievedArticle.get().getRedirectTarget(), "The redirect targets should match");

    }

//
    @Test
    public void getByIdTest() {
        Optional<PageSourcePostgres> articleByKey = articlesDAO.getByKey(testArticle1.getKey());
        assertTrue(articleByKey.isPresent());

        Optional<PageSourcePostgres> retrievedArticle = articlesDAO.getById(articleByKey.get().getId());
        assertTrue(retrievedArticle.isPresent(), "The article should be present in the database");

        assertEquals(testArticle1.getKey(), retrievedArticle.get().getKey(), "The keys should match");
        assertEquals(testArticle1.getTitle(), retrievedArticle.get().getTitle(), "The titles should match");
        assertEquals(testArticle1.getLatest(), retrievedArticle.get().getLatest(), "The latest timestamps should match");
        assertEquals(testArticle1.getContentModel(), retrievedArticle.get().getContentModel(), "The content models should match");
        assertEquals(testArticle1.getSource(), retrievedArticle.get().getSource(), "The sources should match");
        assertEquals(testArticle1.getRedirectTarget(), retrievedArticle.get().getRedirectTarget(), "The redirect targets should match");
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

        assert(articlesDAO.setArticle(pageSourcePostgres).isPresent());

        Optional<PageSourcePostgres> retrievedArticle = articlesDAO.getByKey(pageSourcePostgres.getKey());
        assertTrue(retrievedArticle.isPresent(), "The article should be present in the database");

        assertEquals(pageSourcePostgres.getKey(), retrievedArticle.get().getKey(), "The keys should match");
        assertEquals(pageSourcePostgres.getTitle(), retrievedArticle.get().getTitle(), "The titles should match");
        assertEquals(pageSourcePostgres.getLatest(), retrievedArticle.get().getLatest(), "The latest timestamps should match");
        assertEquals(pageSourcePostgres.getContentModel(), retrievedArticle.get().getContentModel(), "The content models should match");
        assertEquals(pageSourcePostgres.getSource(), retrievedArticle.get().getSource(), "The sources should match");
        assertEquals(pageSourcePostgres.getRedirectTarget(), retrievedArticle.get().getRedirectTarget(), "The redirect targets should match");
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

        assert(articlesDAO.updateArticle(pageSourcePostgres).isPresent());

        Optional<PageSourcePostgres> retrievedArticle = articlesDAO.getByKey(pageSourcePostgres.getKey());
        assertTrue(retrievedArticle.isPresent(), "The article should be present in the database");

        assertEquals(pageSourcePostgres.getKey(), retrievedArticle.get().getKey(), "The keys should match");
        assertEquals(pageSourcePostgres.getTitle(), retrievedArticle.get().getTitle(), "The titles should match");
        assertEquals(pageSourcePostgres.getLatest(), retrievedArticle.get().getLatest(), "The latest timestamps should match");
        assertEquals(pageSourcePostgres.getContentModel(), retrievedArticle.get().getContentModel(), "The content models should match");
        assertEquals(pageSourcePostgres.getSource(), retrievedArticle.get().getSource(), "The sources should match");
        assertEquals(pageSourcePostgres.getRedirectTarget(), retrievedArticle.get().getRedirectTarget(), "The redirect targets should match");
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

        assertTrue(articlesDAO.setArticle(pageSourcePostgres).isPresent(), "The article should be on DB");
        assertTrue(articlesDAO.deleteArticle(pageSourcePostgres), "The article should be deleted on DB");
        assertTrue(articlesDAO.getByKey(pageSourcePostgres.getKey()).isEmpty(), "The article should not be in DB");
    }
}

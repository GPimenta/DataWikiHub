package org.stream_message.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.stream_message.connector.PostgresSQLJDBC;
import org.stream_message.model.PageSourcePostgres;

import java.time.LocalDateTime;
import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ArticlesDAOTest {
    private final String host = "localhost";
    private final String port = "5432";
    private final String database = "WIKI_ARTICLES";
    private final PostgresSQLJDBC postgresSQLJDBC = new PostgresSQLJDBC(host, port, database);
    private final ArticlesDAO articlesDAO = new ArticlesDAO(postgresSQLJDBC);
    private PageSourcePostgres testArticle;

    @BeforeAll
    public void setupAll() {
        testArticle = new PageSourcePostgres.Builder()
                .key("testingKeyGetAll1")
                .title("testingTitleGetAll1")
                .latest(LocalDateTime.now())
                .contentModel("testingContentGetAll1")
                .source("testingSourceGetAll1")
                .redirectTarget("redirectTargetGetByAll1")
                .build();
    }


    @Test
    public void getAllArticles() {
        PageSourcePostgres pageSourcePostgres1 = new PageSourcePostgres.Builder()
                .key("testingKeyGetAll1")
                .title("testingTitleGetAll1")
                .latest(LocalDateTime.now())
                .contentModel("testingContentGetAll1")
                .source("testingSourceGetAll1")
                .redirectTarget("redirectTargetGetByAll1")
                .build();

        PageSourcePostgres pageSourcePostgres2 = new PageSourcePostgres.Builder()
                .key("testingKeyGetAll2")
                .title("testingTitleGetAll2")
                .latest(LocalDateTime.now())
                .contentModel("testingContentGetAll2")
                .source("testingSourceGetAll2")
                .redirectTarget("redirectTargetGetByAll2")
                .build();

        PageSourcePostgres pageSourcePostgres3 = new PageSourcePostgres.Builder()
                .key("testingKeyGetAll3")
                .title("testingTitleGetAll3")
                .latest(LocalDateTime.now())
                .contentModel("testingContentGetAll3")
                .source("testingSourceGetAll3")
                .redirectTarget("redirectTargetGetByAll3")
                .build();
    }

    @Test
    public void getByIdTest() {
        PageSourcePostgres pageSourcePostgres = new PageSourcePostgres.Builder()
                .key("testingKeyGetById1")
                .title("testingTitleGetById1")
                .latest(LocalDateTime.now())
                .contentModel("testingContentGetById1")
                .source("testingSourceGetById1")
                .redirectTarget("redirectTargetGetByIdTest")
                .build();

        if (articlesDAO.setArticle(pageSourcePostgres)) {
            Optional<PageSourcePostgres> byKey = articlesDAO.getById(pageSourcePostgres.getId());
            assert byKey.isPresent() &&  byKey.get().getId() == pageSourcePostgres.getId();
        }
        assert(false);
    }

    @Test
    public void getByKeyTest() {
        PageSourcePostgres pageSourcePostgres = new PageSourcePostgres.Builder()
                .key("testingKeyGetByKey1")
                .title("testingTitleGetByKey1")
                .latest(LocalDateTime.now())
                .contentModel("testingContentGetByKey")
                .source("testingSourceGetByKey")
                .redirectTarget("redirectTargetGetByKeyTest")
                .build();

        if (articlesDAO.setArticle(pageSourcePostgres)) {
            Optional<PageSourcePostgres> byKey = articlesDAO.getByKey(pageSourcePostgres.getKey());
            assert byKey.isPresent() &&  byKey.get().getKey() == pageSourcePostgres.getKey();
        }
        assert(false);
    }


    @Test
    public void setArticleTest() {
        PageSourcePostgres pageSourcePostgres = new PageSourcePostgres.Builder()
                .key("testingKeySetArticle1")
                .title("testingTitleSetArticle1")
                .latest(LocalDateTime.now())
                .contentModel("testingContentSetArticle")
                .source("testingSourceSetArticle")
                .redirectTarget("redirectTargetTestSetArticle")
                .build();

        assert(articlesDAO.setArticle(pageSourcePostgres));
    }

    
}

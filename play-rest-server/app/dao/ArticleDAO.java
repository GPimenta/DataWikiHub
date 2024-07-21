package dao;

import jakarta.persistence.NoResultException;
import models.Article;
import net.jodah.failsafe.CircuitBreaker;
import net.jodah.failsafe.Failsafe;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class ArticleDAO {

    private final JPAApi jpaApi;
    private final PostExecutionContext ec;
    private final CircuitBreaker<Optional<Article>> circuitBreaker = new CircuitBreaker<Optional<Article>>().withFailureThreshold(1).withSuccessThreshold(3);

    @Inject
    public ArticleDAO(JPAApi jpaApi, PostExecutionContext ec) {
        this.jpaApi = jpaApi;
        this.ec = ec;
    }


    public CompletionStage<Stream<Article>> list() {
        return supplyAsync(() -> wrap(em -> select(em)), ec);
    }

    public CompletionStage<Article> create(Article article) {
        return supplyAsync(() -> wrap(em -> insert(em, article)), ec);
    }

    public CompletionStage<Optional<Article>> get(int id) {
        return supplyAsync(() -> wrap(em -> Failsafe.with(circuitBreaker).get(()->lookup(em, id))), ec);
    }

    public CompletionStage<Optional<Article>> getByTitle(String title) {
        return supplyAsync(() -> wrap(em -> Failsafe.with(circuitBreaker).get(() -> lookupByTitle(em,title))),ec);
    }

    public CompletionStage<Optional<Article>> update(int id, Article article) {
        return supplyAsync(() -> wrap(em -> Failsafe.with(circuitBreaker).get(() -> lookup(em, id))),ec);
    }

    public CompletionStage<Optional<Article>> updateByTitle(String title, Article article) {
        return supplyAsync(() -> wrap(em -> Failsafe.with(circuitBreaker).get(() -> lookupByTitle(em, title))), ec);
    }

    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private Stream<Article> select(EntityManager entityManager) {
        TypedQuery<Article> query = entityManager.createQuery("SELECT a FROM Article a", Article.class);
        return query.getResultList().stream();
    }

    private Optional<Article> lookup(EntityManager em, int id) throws SQLException {
//        throw new SQLException("Call this to cause the circuit breaker to trip");
        return Optional.ofNullable(em.find(Article.class, id));
    }

    private Optional<Article> lookupByTitle(EntityManager em, String title) {
        TypedQuery<Article> query = em.createQuery("SELECT a from Article a WHERE a.title = :title", Article.class);
        query.setParameter("title", title);
        try {
            Article article = query.getSingleResult();
            return Optional.of(article);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }


    private Optional<Article> modify(EntityManager em, int id, Article article) throws InterruptedException {
        final Article data = em.find(Article.class, id);
        if (data != null) {
            data.setKey(article.getKey());
            data.setTitle(article.getTitle());
            data.setLatest(article.getLatest());
            data.setContentModel(article.getContentModel());
            data.setSource(article.getSource());
            data.setRedirectTarget(article.getRedirectTarget());
        }
        Thread.sleep(10000L);
        return Optional.ofNullable(data);
    }

    private Optional<Article> modify(EntityManager em, String title, Article article) throws InterruptedException {
        Optional<Article> data = lookupByTitle(em, title);
        if (data.isPresent()) {
            data.get().setKey(article.getKey());
            data.get().setLatest(article.getLatest());
            data.get().setContentModel(article.getContentModel());
            data.get().setSource(article.getSource());
            data.get().setRedirectTarget(article.getRedirectTarget());
        }
        Thread.sleep(10000L);
        return data;
    }

    private Article insert(EntityManager em, Article article) {
        return em.merge(article);
    }
}

package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import models.Article;
import net.jodah.failsafe.Failsafe;

import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public interface ArticleRepository {
    public CompletionStage<Stream<Article>> list() ;

    public CompletionStage<Article> create(Article article);

    public CompletionStage<Optional<Article>> get(int id);

    public CompletionStage<Optional<Article>> getByTitle(String title);

    public CompletionStage<Optional<Article>> update(int id, Article article);

    public CompletionStage<Optional<Article>> updateByTitle(String title, Article article);
}

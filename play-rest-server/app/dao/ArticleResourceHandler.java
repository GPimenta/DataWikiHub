package dao;

import com.palominolabs.http.url.UrlBuilder;
import models.Article;
import models.ArticleResource;
import play.libs.concurrent.ClassLoaderExecutionContext;
import play.mvc.Http;

import javax.inject.Inject;
import java.nio.charset.CharacterCodingException;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;


public class ArticleResourceHandler {

    private final ArticleDAO articleDAO;
    private final ClassLoaderExecutionContext ec;

    @Inject
    public ArticleResourceHandler(ArticleDAO articleDAO, ClassLoaderExecutionContext ec) {
        this.articleDAO = articleDAO;
        this.ec = ec;
    }

    public CompletionStage<Stream<ArticleResource>> find(Http.Request request) {
        return articleDAO.list().thenApplyAsync(articleStream -> {
            return articleStream.map(article -> new ArticleResource(article, link(request, article)));
        }, ec.current());
    }

    public CompletionStage<ArticleResource> create(Http.Request request, ArticleResource articleResource) {
        final Article article = new Article(articleResource.getKey(), articleResource.getTitle(), articleResource.getLatest_version_timestamp(), articleResource.getContent_model(), articleResource.getSource(), articleResource.getRedirectTarget());
        return articleDAO.create(article).thenApplyAsync(savedArticle -> {
            return new ArticleResource(savedArticle, link(request, savedArticle));
        }, ec.current());
    }

    public CompletionStage<Optional<ArticleResource>> lookup(Http.Request request, int id) {
        return articleDAO.get(id).thenApplyAsync(optionalArticle -> {
            return optionalArticle.map(article -> new ArticleResource(article, link(request, article)));
        }, ec.current());
    }

    public CompletionStage<Optional<ArticleResource>> lookupByTitle(Http.Request request, String title) {
        return articleDAO.getByTitle(title).thenApplyAsync(OptionalArticle -> {
            return OptionalArticle.map(article -> new ArticleResource(article, link(request, article)));
        }, ec.current());
    }

    public CompletionStage<Optional<ArticleResource>> update(Http.Request request, int id, ArticleResource articleResource) {
        final Article articleData = new Article(articleResource.getKey(), articleResource.getTitle(), articleResource.getLatest_version_timestamp(), articleResource.getContent_model(), articleResource.getSource(), articleResource.getRedirectTarget());
        return articleDAO.update(id, articleData).thenApplyAsync(optionalArticle -> {
            return optionalArticle.map(article -> new ArticleResource(article, link(request, article)));
        }, ec.current());
    }

    public CompletionStage<Optional<ArticleResource>> updateByTitle(Http.Request request, String title, ArticleResource articleResource) {
        final Article articleData = new Article(articleResource.getKey(), articleResource.getTitle(), articleResource.getLatest_version_timestamp(), articleResource.getContent_model(), articleResource.getSource(), articleResource.getRedirectTarget());
        return articleDAO.updateByTitle(title, articleData).thenApplyAsync(optionalArticle -> {
            return optionalArticle.map(article -> new ArticleResource(article, link(request, article)));
        }, ec.current());
    }

    public CompletionStage<Optional<ArticleResource>> remove(Http.Request request, int id) {
        return articleDAO.remove(id).thenApplyAsync(optionalArticle -> {
            return optionalArticle.map(article -> new ArticleResource(article, link(request, article)));
        });
    }

    public CompletionStage<Optional<ArticleResource>> removeByTitle(Http.Request request, String title) {
        return articleDAO.removeByTitle(title).thenApplyAsync(optionalArticle -> {
            return optionalArticle.map(article -> new ArticleResource(article, link(request, article)));
        });
    }

    private String link(Http.Request request, Article article) {
        final String[] hostPort = request.host().split(":");
        String host = hostPort[0];
        int port = (hostPort.length == 2) ? Integer.parseInt(hostPort[1]) : -1;
        final String scheme = request.secure() ? "https" : "http";
        try {
            return UrlBuilder.forHost(scheme, host, port)
                    .pathSegments("v1", "posts", String.valueOf(article.getId()))
                    .toUrlString();
        } catch (CharacterCodingException e) {
            throw new IllegalStateException(e);
        }
    }



//    public CompletionStage<Stream<ArticleResource>> find(Http.Request request) {
//        return articleDAO.list().thenApplyAsync(articleStream -> {
//            return articleStream.map(article -> new ArticleResource(article, link(request, article)));
//        })
//    }
}

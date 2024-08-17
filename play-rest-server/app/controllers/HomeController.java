package controllers;

import action.ArticleAction;
import com.fasterxml.jackson.databind.JsonNode;
import dao.ArticleResourceHandler;
import models.Article;
import models.ArticleResource;
import play.libs.Json;
import play.libs.concurrent.ClassLoaderExecutionContext;
import play.mvc.*;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;


/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
@With(ArticleAction.class)
public class HomeController extends Controller {

    private ClassLoaderExecutionContext ec;
    private ArticleResourceHandler handler;

    @Inject
    public HomeController(ClassLoaderExecutionContext ec, ArticleResourceHandler handler) {
        this.ec = ec;
        this.handler = handler;
    }

    public CompletionStage<Result> list(Http.Request request) {
        return handler.find(request).thenApplyAsync(articles -> {
            final List<ArticleResource> articleList = articles.collect(Collectors.toList());
            return ok(views.html.articles.render(articleList));
        }, ec.current());
    }

    public CompletionStage<Result> show(Http.Request request, int id) {
        return handler.lookup(request, id).thenApplyAsync(optionalArticle ->
                optionalArticle.map(articleResource -> ok(views.html.article.render(articleResource))).orElseGet(Results::notFound), ec.current());
    }

    public CompletionStage<Result> showByTitle(Http.Request request, String title) {
        return handler.lookupByTitle(request, title).thenApplyAsync(optionalArticle ->
                optionalArticle.map(articleResource -> ok(views.html.article.render(articleResource))).orElseGet(Results::notFound), ec.current());
    }

    public CompletionStage<Result> update(Http.Request request, int id) {
        JsonNode json = request.body().asJson();
        ArticleResource articleResource = Json.fromJson(json, ArticleResource.class);
        return handler.update(request, id, articleResource).thenApplyAsync(optionalArticleResource ->
                optionalArticleResource.map(articleResourceJson -> ok(views.html.updated.render(articleResourceJson))).orElseGet(Results::notFound), ec.current());
    }

    public CompletionStage<Result> updateByTitle(Http.Request request, String title) {
        JsonNode json = request.body().asJson();
        ArticleResource articleResource = Json.fromJson(json, ArticleResource.class);
        return handler.updateByTitle(request, title, articleResource).thenApplyAsync(optionalArticleResource ->
                optionalArticleResource.map(articleResourceJson -> ok(views.html.updated.render(articleResourceJson))).orElseGet(Results::notFound), ec.current());
    }

    public CompletionStage<Result> create(Http.Request request) {
        JsonNode json = request.body().asJson();
        final ArticleResource articleResource = Json.fromJson(json, ArticleResource.class);
        return handler.create(request, articleResource).thenApplyAsync(savedArticleResource ->
                created(views.html.create.render(savedArticleResource)), ec.current());
    }

    public CompletionStage<Result> remove(Http.Request request, int id) {
//        JsonNode json = request.body().asJson();
        return handler.remove(request, id).thenApplyAsync(deletedArticleResource ->
                deletedArticleResource.map(articleResourceDeleted -> ok(views.html.delete.render(articleResourceDeleted))).orElseGet(Results::notFound), ec.current());
    }

    public CompletionStage<Result> removeByTitle(Http.Request request, String title) {
        return handler.removeByTitle(request, title).thenApplyAsync(deletedArticleResource ->
                deletedArticleResource.map(articleResourceDeleted -> ok(views.html.delete.render(articleResourceDeleted))).orElseGet(Results::notFound), ec.current());
    }



    //    private PostResourceHandler handler

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(views.html.index.render());
    }

//    @BodyParser.Of(BodyParser.Json.class)
//    public Result createArticle(Http.Request request) {
//        JsonNode jsonNode = request.body().asJson();
//        Article article = Json.fromJson(jsonNode, Article.class);
//        System.out.println(article);
//        return ok();
//    }
}

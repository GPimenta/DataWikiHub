package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Article;
import play.libs.Json;
import play.mvc.*;

import javax.inject.Inject;
import java.time.chrono.JapaneseEra;
import java.util.List;
import java.util.Optional;


/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(views.html.index.render());
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result createArticle(Http.Request request) {
        JsonNode jsonNode = request.body().asJson();
        Article article = Json.fromJson(jsonNode, Article.class);
        System.out.println(article);
        return ok();
    }

//    @Inject
//    private ArticleDAO articleDAO;
//
//    public Result getAllArticles() {
//        List<Article> articles = articleDAO.findAll();
//        return ok(Json.toJson(articles));
//    }
//
//    public Result getArticleById(int id) {
//        Optional<Article> byId = articleDAO.findById(id);
//
//        if (byId.isPresent()) {
//            return ok(Json.toJson(byId.get()));
//        }
//        else {
//            return notFound("Article not found by Id");
//        }
//    }
//
//    public Result getArticleByTitle(String title) {
//        Optional<Article> byTitle = articleDAO.findByTitle(title);
//
//        if (byTitle.isPresent()) {
//            return ok(Json.toJson(byTitle.get()));
//        }
//        else {
//            return notFound("Article not found by Title");
//        }
//    }

//    public Result createArticle() {
//        JsonNode json = request
//    }

}

package org.stream_message.controller;

import org.stream_message.model.PageSourcePostgres;
import org.stream_message.repository.ArticlesDAO;

import java.util.List;

public class ArticlesController {

    private final ArticlesDAO articlesDAO;

    public ArticlesController(ArticlesDAO articlesDAO) {
        this.articlesDAO = articlesDAO;
    }

    public List<PageSourcePostgres> getAllArticles(){
       return articlesDAO.getAllArticles().orElseThrow(() -> new RuntimeException("Unable to get all articles"));

    }

    public PageSourcePostgres getById(int id) {
        return articlesDAO.getById(id).orElseThrow(() -> new RuntimeException("Unable to get article by Id"));
    }

    public PageSourcePostgres getByKey(String key) {
       return articlesDAO.getByKey(key).orElseThrow(() -> new RuntimeException("Unable to get articles by key"));
    }

    public PageSourcePostgres setArticle(PageSourcePostgres pageSourcePostgres) {
        return articlesDAO.setArticle(pageSourcePostgres).orElseThrow(() -> new RuntimeException("Unable to set the article on the DB"));
    }

    public PageSourcePostgres updateArticle(PageSourcePostgres pageSourcePostgres) {
        return articlesDAO.updateArticle(pageSourcePostgres). orElseThrow(() -> new RuntimeException("Unable to update the article on the DB"));
    }

    public boolean deleteArticle(PageSourcePostgres pageSourcePostgres) {
        return articlesDAO.deleteArticle(pageSourcePostgres);
    }

}

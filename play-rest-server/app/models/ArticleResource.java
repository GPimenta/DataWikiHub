package models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Objects;

public class ArticleResource {

    private int id;
    private String link;
    private String key;
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime latest;
    private String contentModel;
    private String source;
    private String redirectTarget;

    public ArticleResource() {
    }

    public ArticleResource(int id, String link, String key, String title, LocalDateTime latest, String contentModel, String source, String redirectTarget) {
        this.link = link;
        this.key = key;
        this.title = title;
        this.latest = latest;
        this.contentModel = contentModel;
        this.source = source;
        this.redirectTarget = redirectTarget;
        System.out.println(this);
    }

    public ArticleResource(Article article, String link) {
        this.id = article.getId();
        this.link = link;
        this.key = article.getKey();
        this.title = article.getTitle();
        this.latest = article.getLatest();
        this.contentModel = article.getContentModel();
        this.source = article.getSource();
        this.redirectTarget = article.getRedirectTarget();
        System.out.println(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getLatest() {
        return latest;
    }

    public void setLatest(LocalDateTime latest) {
        this.latest = latest;
    }

    public String getContentModel() {
        return contentModel;
    }

    public void setContentModel(String contentModel) {
        this.contentModel = contentModel;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRedirectTarget() {
        return redirectTarget;
    }

    public void setRedirectTarget(String redirectTarget) {
        this.redirectTarget = redirectTarget;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleResource that = (ArticleResource) o;
        return id == that.id && Objects.equals(link, that.link) && Objects.equals(key, that.key) && Objects.equals(title, that.title) && Objects.equals(latest, that.latest) && Objects.equals(contentModel, that.contentModel) && Objects.equals(source, that.source) && Objects.equals(redirectTarget, that.redirectTarget);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, link, key, title, latest, contentModel, source, redirectTarget);
    }

    @Override
    public String toString() {
        return "ArticleResource{" +
                "id=" + id +
                ", link='" + link + '\'' +
                ", key='" + key + '\'' +
                ", title='" + title + '\'' +
                ", latest=" + latest +
                ", contentModel='" + contentModel + '\'' +
                ", source='" + source + '\'' +
                ", redirectTarget='" + redirectTarget + '\'' +
                '}';
    }
}

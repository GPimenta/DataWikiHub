package models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;

import java.time.LocalDateTime;
import java.util.Objects;

public class ArticleResource {

    private int id;
    private String link;
    @Column(name = "key")
    private String key;
    @Column(name = "title", unique = true)
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "latest_version_timestamp")
    private LocalDateTime latest_version_timestamp;
    @Column(name = "content_model", nullable = false)
    private String content_model;
    @Column(name = "source")
    private String source;
    @JsonProperty("redirect_target")
    @Column(name = "redirect_target")
    private String redirectTarget;

    public ArticleResource() {}

    public ArticleResource(String link, String key, String title, LocalDateTime latest, String contentModel, String source, String redirectTarget) {
        this.link = link;
        this.key = key;
        this.title = title;
        this.latest_version_timestamp = latest;
        this.content_model = contentModel;
        this.source = source;
        this.redirectTarget = redirectTarget;
        System.out.println(this);
    }

    public ArticleResource(Article article, String link) {
        this.id = article.getId();
        this.link = link;
        this.key = article.getKey();
        this.title = article.getTitle();
        this.latest_version_timestamp = article.getLatest_version_timestamp();
        this.content_model = article.getContent_model();
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

    public LocalDateTime getLatest_version_timestamp() {
        return latest_version_timestamp;
    }

    public void setLatest_version_timestamp(LocalDateTime latest_version_timestamp) {
        this.latest_version_timestamp = latest_version_timestamp;
    }

    public String getContent_model() {
        return content_model;
    }

    public void setContent_model(String content_model) {
        this.content_model = content_model;
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
        return id == that.id && Objects.equals(link, that.link) && Objects.equals(key, that.key) && Objects.equals(title, that.title) && Objects.equals(latest_version_timestamp, that.latest_version_timestamp) && Objects.equals(content_model, that.content_model) && Objects.equals(source, that.source) && Objects.equals(redirectTarget, that.redirectTarget);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, link, key, title, latest_version_timestamp, content_model, source, redirectTarget);
    }

    @Override
    public String toString() {
        return "ArticleResource{" +
                "id=" + id +
                ", link='" + link + '\'' +
                ", key='" + key + '\'' +
                ", title='" + title + '\'' +
                ", latest=" + latest_version_timestamp +
                ", contentModel='" + content_model + '\'' +
                ", source='" + source + '\'' +
                ", redirectTarget='" + redirectTarget + '\'' +
                '}';
    }
}

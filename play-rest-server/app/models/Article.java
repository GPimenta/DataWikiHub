package models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "articles")
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String key;
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime latest;
    private String contentModel;
    private String source;
    private String redirectTarget;

    public Article() {}

    public Article( String key, String title, LocalDateTime latest, String contentModel, String source, String redirectTarget) {
        this.key = key;
        this.title = title;
        this.latest = latest;
        this.contentModel = contentModel;
        this.source = source;
        this.redirectTarget = redirectTarget;
        System.out.println(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        Article article = (Article) o;
        return id == article.id && Objects.equals(key, article.key) && Objects.equals(title, article.title) && Objects.equals(latest, article.latest) && Objects.equals(contentModel, article.contentModel) && Objects.equals(source, article.source) && Objects.equals(redirectTarget, article.redirectTarget);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, key, title, latest, contentModel, source, redirectTarget);
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", title='" + title + '\'' +
                ", latest=" + latest +
                ", contentModel='" + contentModel + '\'' +
                ", source='" + source + '\'' +
                ", redirectTarget='" + redirectTarget + '\'' +
                '}';
    }
}

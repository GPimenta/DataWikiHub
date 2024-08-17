package models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @Column(name = "key")
    private String key;
    @Column(name = "title", unique = true)
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "latest_version_timestamp")
    private LocalDateTime latest_version_timestamp;
    @JsonProperty("content_model")
    @Column(name = "content_model", nullable = false)
    private String content_model;
    @Column(name = "source")
    private String source;
    @JsonProperty("redirect_target")
    @Column(name = "redirect_target")
    private String redirectTarget;

    public Article() {}

    public Article( String key, String title, LocalDateTime latest, String contentModel, String source, String redirectTarget) {
        this.key = key;
        this.title = title;
        this.latest_version_timestamp = latest;
        this.content_model = contentModel;
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
        Article article = (Article) o;
        return id == article.id && Objects.equals(key, article.key) && Objects.equals(title, article.title) && Objects.equals(latest_version_timestamp, article.latest_version_timestamp) && Objects.equals(content_model, article.content_model) && Objects.equals(source, article.source) && Objects.equals(redirectTarget, article.redirectTarget);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, key, title, latest_version_timestamp, content_model, source, redirectTarget);
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", title='" + title + '\'' +
                ", latest=" + latest_version_timestamp +
                ", contentModel='" + content_model + '\'' +
                ", source='" + source + '\'' +
                ", redirectTarget='" + redirectTarget + '\'' +
                '}';
    }
}

package org.stream_message.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

public class PageSourcePostgres {
    private final int id;
    private final String key;
    private final String title;
    private final LocalDateTime latest;
    private final String contentModel;
    private final String source;
    @SerializedName("optionalField")
    @Nullable
    private final String redirectTarget;

    public static class Builder {
        private int id;
        private String key;
        private String title;
        private LocalDateTime latest;
        private String contentModel;
        private String source;
        private String redirectTarget;

        public Builder id(int val) {
            id = val; return this;
        }

        public Builder key(String val) {
            key = val; return this;
        }

        public Builder title(String val) {
            title = val; return this;
        }

        public Builder latest(LocalDateTime val) {
            latest = val; return this;
        }

        public Builder contentModel(String val) {
            contentModel = val; return this;
        }

        public Builder source(String val) {
            source = val; return this;
        }

        public Builder redirectTarget(String val) {
            redirectTarget = val; return this;
        }

        public PageSourcePostgres build(){
            return new PageSourcePostgres(this);
        }


    }

    private PageSourcePostgres(Builder builder) {
        id = builder.id;
        key = builder.key;
        title = builder.title;
        latest = builder.latest;
        contentModel = builder.contentModel;
        source = builder.source;
        redirectTarget = builder.redirectTarget;
    }

    public int getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getLatest() {
        return latest;
    }

    public String getContentModel() {
        return contentModel;
    }

    public String getSource() {
        return source;
    }

    @Nullable
    public String getRedirectTarget() {
        return redirectTarget;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageSourcePostgres that = (PageSourcePostgres) o;
        return id == that.id && Objects.equals(key, that.key) && Objects.equals(title, that.title) && Objects.equals(latest, that.latest) && Objects.equals(contentModel, that.contentModel) && Objects.equals(source, that.source) && Objects.equals(redirectTarget, that.redirectTarget);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, key, title, latest, contentModel, source, redirectTarget);
    }

    @Override
    public String toString() {
        return "PageSource{" +
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

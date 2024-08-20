package org.wikipedia.model;

import com.google.gson.annotations.SerializedName;
import org.wikipedia.model.Latest;
import org.wikipedia.model.License;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class PageSource {
    private final int id;
    private final String key;
    private final String title;
    private final Latest latest;
    private final String contentModel;
    private final License license;
    private final String source;
    @SerializedName("optionalField")
    @Nullable
    private final String redirectTarget;

    public static class Builder {
        private int id;
        private String key;
        private String title;
        private Latest latest;
        private String contentModel;
        private License license;
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

        public Builder latest(Latest val) {
            latest = val; return this;
        }

        public Builder contentModel(String val) {
            contentModel = val; return this;
        }

        public Builder licence(License val) {
            license = val; return this;
        }
        public Builder source(String val) {
            source = val; return this;
        }

        public Builder redirectTarget(String val) {
            redirectTarget = val; return this;
        }


    }

    private PageSource(Builder builder) {
        id = builder.id;
        key = builder.key;
        title = builder.title;
        latest = builder.latest;
        contentModel = builder.contentModel;
        license = builder.license;
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

    public Latest getLatest() {
        return latest;
    }

    public String getContentModel() {
        return contentModel;
    }

    public License getLicense() {
        return license;
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
        PageSource that = (PageSource) o;
        return id == that.id && Objects.equals(key, that.key) && Objects.equals(title, that.title) && Objects.equals(latest, that.latest) && Objects.equals(contentModel, that.contentModel) && Objects.equals(license, that.license) && Objects.equals(source, that.source) && Objects.equals(redirectTarget, that.redirectTarget);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, key, title, latest, contentModel, license, source, redirectTarget);
    }

    @Override
    public String toString() {
        return "PageSource{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", title='" + title + '\'' +
                ", latest=" + latest +
                ", contentModel='" + contentModel + '\'' +
                ", license=" + license +
                ", source='" + source + '\'' +
                ", redirectTarget='" + redirectTarget + '\'' +
                '}';
    }
}

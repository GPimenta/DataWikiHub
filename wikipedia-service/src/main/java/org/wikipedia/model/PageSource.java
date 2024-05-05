package org.wikipedia.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class PageSource {
    private int id;
    private String key;
    private String title;
    private Latest latest;
    private String contentModel;
    private License license;
    private String source;

    @SerializedName("optionalField")
    @Nullable
    private String redirectTarget;

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

    public Latest getLatest() {
        return latest;
    }

    public void setLatest(Latest latest) {
        this.latest = latest;
    }

    public String getContentModel() {
        return contentModel;
    }

    public void setContentModel(String contentModel) {
        this.contentModel = contentModel;
    }

    public License getLicense() {
        return license;
    }

    public void setLicense(License license) {
        this.license = license;
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

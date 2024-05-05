package org.wikipedia.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nullable;
import java.util.Objects;

public class PageSourceWithHTML {
    private int id;
    private String key;
    private String title;
    private Latest latest;
    private String contentModel;
    private License license;
    private String html;
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

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    @Nullable
    public String getRedirectTarget() {
        return redirectTarget;
    }

    public void setRedirectTarget(@Nullable String redirectTarget) {
        this.redirectTarget = redirectTarget;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageSourceWithHTML that = (PageSourceWithHTML) o;
        return id == that.id && Objects.equals(key, that.key) && Objects.equals(title, that.title) && Objects.equals(latest, that.latest) && Objects.equals(contentModel, that.contentModel) && Objects.equals(license, that.license) && Objects.equals(html, that.html) && Objects.equals(redirectTarget, that.redirectTarget);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, key, title, latest, contentModel, license, html, redirectTarget);
    }

    @Override
    public String toString() {
        return "PageSourceWithHTML{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", title='" + title + '\'' +
                ", latest=" + latest +
                ", contentModel='" + contentModel + '\'' +
                ", license=" + license +
                ", html='" + html + '\'' +
                ", redirectTarget='" + redirectTarget + '\'' +
                '}';
    }
}

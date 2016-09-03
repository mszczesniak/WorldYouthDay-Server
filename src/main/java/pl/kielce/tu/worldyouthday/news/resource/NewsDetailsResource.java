package pl.kielce.tu.worldyouthday.news.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Łukasz Wesołowski on 25.05.2016.
 */
public class NewsDetailsResource implements Serializable {
    private String title;
    private String text;

    @JsonCreator
    public NewsDetailsResource(@JsonProperty("title") String title,
                               @JsonProperty("text") String text) {
        this.title = title;
        this.text = text;
    }


    public NewsDetailsResource(Builder builder) {
        title = builder.title;
        text = builder.text;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewsDetailsResource that = (NewsDetailsResource) o;

        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        return text != null ? text.equals(that.text) : that.text == null;

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NewsDetailsResource{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    public static class Builder {
        private String title;
        private String text;

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withText(String text) {
            this.text = text;
            return this;
        }

        public NewsDetailsResource build() {
            return new NewsDetailsResource(this);
        }
    }
}

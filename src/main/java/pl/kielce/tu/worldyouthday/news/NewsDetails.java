package pl.kielce.tu.worldyouthday.news;

import pl.kielce.tu.worldyouthday.language.Language;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Łukasz Wesołowski on 28.04.2016.
 */

@Entity
@Table(name = "NEWS_DETAILS")
public class NewsDetails {
    @EmbeddedId
    private NewsDetailsPK id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "text", nullable = false)
    private String text;

    public NewsDetails() {
    }

    public NewsDetails(String id, Language language, String title, String text) {
        this.id = new NewsDetailsPK(id, language);
        this.title = title;
        this.text = text;
    }

    public NewsDetailsPK getId() {
        return id;
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

        NewsDetails that = (NewsDetails) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        return text != null ? text.equals(that.text) : that.text == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NewsDetails{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}

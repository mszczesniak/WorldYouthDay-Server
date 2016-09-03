package pl.kielce.tu.worldyouthday.news;

import pl.kielce.tu.worldyouthday.language.Language;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

/**
 * Created by Łukasz Wesołowski on 28.04.2016.
 */

@Embeddable
public class NewsDetailsPK implements Serializable {
    @Column(name = "id")
    private String id;

    @Column(name = "language")
    @Enumerated(EnumType.STRING)
    private Language language;

    private NewsDetailsPK() {
    }

    public NewsDetailsPK(String id, Language language) {
        this.id = id;
        this.language = language;
    }

    public String getId() {
        return id;
    }

    public Language getLanguage() {
        return language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewsDetailsPK that = (NewsDetailsPK) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return language == that.language;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (language != null ? language.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NewsDetailsPK{" +
                "id='" + id + '\'' +
                ", language=" + language +
                '}';
    }
}
package pl.kielce.tu.worldyouthday.news;

import pl.kielce.tu.worldyouthday.cities.City;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.user.User;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Łukasz Wesołowski on 28.04.2016.
 */

@Entity
@Table(name = "NEWS")
public class News {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "creation_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "creation_author", referencedColumnName = "id")
    private User creationAuthor;

    @Column(name = "modification_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificationDate;

    @ManyToOne
    @JoinColumn(name = "modification_author", referencedColumnName = "id")
    private User modificationAuthor;

    @Version
    @Column(name = "version")
    private Long version;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @MapKey(name = "id.language")
    private Map<Language, NewsDetails> details = new HashMap<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    public News() {
    }

    public News(String id, Date creationDate, User creationAuthor, Date modificationDate, User modificationAuthor, Long version, Map<Language, NewsDetails> details, City city) {
        this.id = id;
        this.creationDate = creationDate;
        this.creationAuthor = creationAuthor;
        this.modificationDate = modificationDate;
        this.modificationAuthor = modificationAuthor;
        this.version = version;
        this.details = details;
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public User getCreationAuthor() {
        return creationAuthor;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public User getModificationAuthor() {
        return modificationAuthor;
    }

    public Long getVersion() {
        return version;
    }

    public City getCity() {
        return city;
    }

    public Map<Language, NewsDetails> getDetails() {
        return details;
    }

    public NewsDetails getDetails(Language language) {
        return details.getOrDefault(language, details.get(Language.getDefault()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        News news = (News) o;

        if (id != null ? !id.equals(news.id) : news.id != null) return false;
        if (creationDate != null ? !creationDate.equals(news.creationDate) : news.creationDate != null) return false;
        if (creationAuthor != null ? !creationAuthor.equals(news.creationAuthor) : news.creationAuthor != null)
            return false;
        if (modificationDate != null ? !modificationDate.equals(news.modificationDate) : news.modificationDate != null)
            return false;
        if (modificationAuthor != null ? !modificationAuthor.equals(news.modificationAuthor) : news.modificationAuthor != null)
            return false;
        if (version != null ? !version.equals(news.version) : news.version != null) return false;
        if (details != null ? !details.equals(news.details) : news.details != null) return false;
        return city != null ? city.equals(news.city) : news.city == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (creationAuthor != null ? creationAuthor.hashCode() : 0);
        result = 31 * result + (modificationDate != null ? modificationDate.hashCode() : 0);
        result = 31 * result + (modificationAuthor != null ? modificationAuthor.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (details != null ? details.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "News{" +
                "id='" + id + '\'' +
                ", creationDate=" + creationDate +
                ", creationAuthor=" + creationAuthor +
                ", modificationDate=" + modificationDate +
                ", modificationAuthor=" + modificationAuthor +
                ", version=" + version +
                ", details=" + details +
                ", city=" + city +
                '}';
    }
}

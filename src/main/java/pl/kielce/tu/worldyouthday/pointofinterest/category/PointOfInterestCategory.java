package pl.kielce.tu.worldyouthday.pointofinterest.category;

import pl.kielce.tu.worldyouthday.language.Language;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vooka on 05.04.2016.
 */

@Entity
@Table(name = "POINTS_OF_INTEREST_CATEGORY")
public class PointOfInterestCategory implements Serializable {
    @Id
    @Column(name = "id_poi_category")
    private String id;

    @Version
    @Column(name = "version")
    private Long version;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_poi_category", referencedColumnName = "id_poi_category", insertable = false, updatable = false)
    @MapKey(name = "id.language")
    private Map<Language, PointOfInterestCategoryDetails> details = new HashMap<>();

    PointOfInterestCategory() {
    }

    public PointOfInterestCategory(String id, Long version, Map<Language, PointOfInterestCategoryDetails> details) {
        this.id = id;
        this.version = version;
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public void setDetails(Map<Language, PointOfInterestCategoryDetails> details) {
        this.details = details;
    }

    public Map<Language, PointOfInterestCategoryDetails> getDetails() {
        return details;
    }

    public PointOfInterestCategoryDetails getDetails(Language language) {
        return details.getOrDefault(language, details.get(Language.getDefault()));
    }

    public Long getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PointOfInterestCategory that = (PointOfInterestCategory) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return version != null ? version.equals(that.version) : that.version == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PointOfInterestCategory{" +
                "id='" + id + '\'' +
                ", version=" + version +
                ", details=" + details +
                '}';
    }
}

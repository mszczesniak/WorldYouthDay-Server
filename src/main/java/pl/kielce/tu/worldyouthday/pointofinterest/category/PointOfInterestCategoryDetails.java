package pl.kielce.tu.worldyouthday.pointofinterest.category;

import pl.kielce.tu.worldyouthday.language.Language;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Łukasz Wesołowski on 23.04.2016.
 */

@Entity
@Table(name = "POINTS_OF_INTEREST_CATEGORY_DETAILS")
public class PointOfInterestCategoryDetails {
    @EmbeddedId
    private PointOfInterestCategoryDetailsPK id;

    @Column(name = "name", nullable = false)
    private String name;

    PointOfInterestCategoryDetails() {
    }

    public PointOfInterestCategoryDetails(String id, Language language, String name) {
        this.id = new PointOfInterestCategoryDetailsPK(id, language);
        this.name = name;
    }

    public PointOfInterestCategoryDetailsPK getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PointOfInterestCategoryDetails that = (PointOfInterestCategoryDetails) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PointOfInterestCategoryDetails{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

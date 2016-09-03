package pl.kielce.tu.worldyouthday.pointofinterest;

import pl.kielce.tu.worldyouthday.language.Language;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by vooka on 31.03.2016.
 */
@Entity
@Table(name = "POINTS_OF_INTEREST_DETAILS")
public class PointOfInterestDetails implements Serializable {
    @EmbeddedId
    private PointOfInterestDetailsPK id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    PointOfInterestDetails() {
    }

    public PointOfInterestDetails(String id, Language language, String name, String description) {
        this.id = new PointOfInterestDetailsPK(id, language);
        this.name = name;
        this.description = description;
    }


    public PointOfInterestDetailsPK getId() {
        return id;
    }

    public void setId(PointOfInterestDetailsPK id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PointOfInterestDetails that = (PointOfInterestDetails) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return description != null ? description.equals(that.description) : that.description == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PointOfInterestDetails{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

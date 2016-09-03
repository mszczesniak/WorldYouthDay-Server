package pl.kielce.tu.worldyouthday.pointofinterest;

import pl.kielce.tu.worldyouthday.cities.City;
import pl.kielce.tu.worldyouthday.file.File;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.pointofinterest.category.PointOfInterestCategory;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vooka on 31.03.2016.
 */
@Entity
@Table(name = "POINTS_OF_INTEREST")
public class PointOfInterest implements Serializable {
    @Id
    @Column(name = "id_poi")
    private String id;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Version
    @Column(name = "version")
    private Long version;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id_poi_category", nullable = false)
    private PointOfInterestCategory category;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "poi_id", referencedColumnName = "id_poi", insertable = false, updatable = false)
    @MapKey(name = "id.language")
    private Map<Language, PointOfInterestDetails> details = new HashMap<>();

    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @ManyToMany
    @JoinTable(
            name="POINTS_OF_INTEREST_IMAGES",
            joinColumns=@JoinColumn(name="id_poi", referencedColumnName="id_poi"),
            inverseJoinColumns=@JoinColumn(name="id_file", referencedColumnName="id"))
    private List<File> images = new ArrayList<>();

    protected PointOfInterest() {
    }

    public PointOfInterest(String id, Double latitude, Double longitude, Long version, PointOfInterestCategory category, Map<Language, PointOfInterestDetails> details, City city, List<File> images) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.version = version;
        this.category = category;
        this.details = details;
        this.city = city;
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Long getVersion() {
        return version;
    }

    public Map<Language, PointOfInterestDetails> getDetails() {
        return details;
    }

    public void setDetails(Map<Language, PointOfInterestDetails> details) {
        this.details = details;
    }

    public PointOfInterestDetails getDetails(Language language) {
        return details.getOrDefault(language, details.get(Language.getDefault()));
    }

    public PointOfInterestCategory getCategory() {
        return category;
    }

    public void setCategory(PointOfInterestCategory category) {
        this.category = category;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void addImage(File image) {
        images.add(image);
    }

    public void removeImage(File image) {
        images.remove(image);
    }

    public List<File> getImages() {
        return images;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PointOfInterest that = (PointOfInterest) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null) return false;
        if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (details != null ? !details.equals(that.details) : that.details != null) return false;
        return city != null ? city.equals(that.city) : that.city == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (details != null ? details.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PointOfInterest{" +
                "id='" + id + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", version=" + version +
                ", category=" + category +
                ", details=" + details +
                ", city=" + city +
                '}';
    }
}

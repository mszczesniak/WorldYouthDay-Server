package pl.kielce.tu.worldyouthday.scheduler;


import pl.kielce.tu.worldyouthday.cities.City;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.pointofinterest.PointOfInterest;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "EVENT")
public class Event implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "event_id", referencedColumnName = "id", insertable = false, updatable = false)
    @MapKey(name = "id.language")
    private Map<Language, EventDetails> details = new HashMap<>();

    @ManyToMany
    @JoinTable(
            name = "EVENT_LOCATION",
            joinColumns = @JoinColumn(name = "ID_EVENT", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ID_LOCATION", referencedColumnName = "id_poi")
    )
    private List<PointOfInterest> locations;

    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    protected Event() {
    }

    public Event(String id, List<PointOfInterest> locations, Date startDate, Date endDate, Map<Language, EventDetails> details, City city, Long version) {
        this.id = id;
        this.locations = locations;
        this.startDate = startDate;
        this.endDate = endDate;
        this.details = details;
        this.city = city;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public Map<Language, EventDetails> getDetails() {
        return details;
    }

    public EventDetails getDetails(Language language) {
        return details.getOrDefault(language, details.get(Language.getDefault()));
    }

    public List<PointOfInterest> getLocations() {
        return locations;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public City getCity() {
        return city;
    }

    public Long getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (id != null ? !id.equals(event.id) : event.id != null) return false;
        if (details != null ? !details.equals(event.details) : event.details != null) return false;
        if (locations != null ? !locations.equals(event.locations) : event.locations != null) return false;
        if (startDate != null ? !startDate.equals(event.startDate) : event.startDate != null) return false;
        if (endDate != null ? !endDate.equals(event.endDate) : event.endDate != null) return false;
        if (city != null ? !city.equals(event.city) : event.city != null) return false;
        return version != null ? version.equals(event.version) : event.version == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (details != null ? details.hashCode() : 0);
        result = 31 * result + (locations != null ? locations.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", details=" + details +
                ", locations=" + locations +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", city=" + city +
                ", version=" + version +
                '}';
    }
}

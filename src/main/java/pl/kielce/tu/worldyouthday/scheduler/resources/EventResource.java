package pl.kielce.tu.worldyouthday.scheduler.resources;

import pl.kielce.tu.worldyouthday.language.Language;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.*;

@XmlRootElement
public class EventResource implements Serializable {

    private final String id;
    private final Map<Language, EventDetailsResource> details;
    private final List<String> locationsId;
    private final Date startDate;
    private final Date endDate;
    private final String cityId;
    private final Long version;

    public EventResource(Builder builder) {
        this.id = builder.id;
        this.details = builder.details;
        this.locationsId = builder.locationsId;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.cityId = builder.cityId;
        this.version = builder.version;
    }

    public String getId() {
        return id;
    }

    public Map<Language, EventDetailsResource> getDetails() {
        return details;
    }

    public List<String> getLocationsId() {
        return locationsId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getCityId() {
        return cityId;
    }

    public Long getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventResource that = (EventResource) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (details != null ? !details.equals(that.details) : that.details != null) return false;
        if (locationsId != null ? !locationsId.equals(that.locationsId) : that.locationsId != null) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (cityId != null ? !cityId.equals(that.cityId) : that.cityId != null) return false;
        return version != null ? version.equals(that.version) : that.version == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (details != null ? details.hashCode() : 0);
        result = 31 * result + (locationsId != null ? locationsId.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (cityId != null ? cityId.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EventResource{" +
                "id='" + id + '\'' +
                ", details=" + details +
                ", locationsId=" + locationsId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", cityId='" + cityId + '\'' +
                ", version=" + version +
                '}';
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private Map<Language, EventDetailsResource> details = new HashMap<>();
        private List<String> locationsId = new ArrayList<>();
        private Date startDate;
        private Date endDate;
        private String cityId;
        private Long version;


        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withDetails(Language language, EventDetailsResource details) {
            this.details.put(language, details);
            return this;
        }

        public Builder withDetails(Map<Language, EventDetailsResource> details) {
            this.details = details;
            return this;
        }

        public Builder withLocationsId(List<String> locationsId) {
            this.locationsId = locationsId;
            return this;
        }

        public Builder withLocationsId(String locationId) {
            this.locationsId.add(locationId);
            return this;
        }

        public Builder withStartDate(Date startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder withEndDate(Date endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder withCityId(String cityId) {
            this.cityId = cityId;
            return this;
        }

        public Builder withVersion(Long version) {
            this.version = version;
            return this;
        }

        public EventResource build() {
            return new EventResource(this);
        }

    }


}

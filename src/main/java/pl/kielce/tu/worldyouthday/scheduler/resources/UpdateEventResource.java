package pl.kielce.tu.worldyouthday.scheduler.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.kielce.tu.worldyouthday.language.Language;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@XmlRootElement
public class UpdateEventResource implements Serializable {

    private final String id;
    private final List<String> locationsId;
    private final String cityId;
    private final Date startDate;
    private final Date endDate;
    private final HashMap<Language, UpdateEventDetailsResource> details;
    private final Long version;

    @JsonCreator
    public UpdateEventResource(
            @JsonProperty("id") String id,
            @JsonProperty("locationsId") List<String> locationsId,
            @JsonProperty("cityId") String cityId,
            @JsonProperty("startDate") Date startDate,
            @JsonProperty("endDate") Date endDate,
            @JsonProperty("details") HashMap<Language, UpdateEventDetailsResource> details,
            @JsonProperty("version") Long version) {
        this.id = id;
        this.locationsId = locationsId;
        this.cityId = cityId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.details = details;
        this.version = version;
    }

    public UpdateEventResource(Builder builder) {
        this.id = builder.id;
        this.locationsId = builder.locationsId;
        this.cityId = builder.cityId;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.details = builder.details;
        this.version = builder.version;
    }

    public String getId() {
        return id;
    }

    public List<String> getLocationsId() {
        return locationsId;
    }

    public String getCityId() {
        return cityId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public HashMap<Language, UpdateEventDetailsResource> getDetails() {
        return details;
    }

    public Long getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdateEventResource that = (UpdateEventResource) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (locationsId != null ? !locationsId.equals(that.locationsId) : that.locationsId != null) return false;
        if (cityId != null ? !cityId.equals(that.cityId) : that.cityId != null) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (details != null ? !details.equals(that.details) : that.details != null) return false;
        return version != null ? version.equals(that.version) : that.version == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (locationsId != null ? locationsId.hashCode() : 0);
        result = 31 * result + (cityId != null ? cityId.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (details != null ? details.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UpdateEventResource{" +
                "id='" + id + '\'' +
                ", locationsId=" + locationsId +
                ", cityId='" + cityId + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", details=" + details +
                ", version=" + version +
                '}';
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private List<String> locationsId = new ArrayList<>();
        private String cityId;
        private Date startDate;
        private Date endDate;
        private HashMap<Language, UpdateEventDetailsResource> details = new HashMap<>();
        private Long version;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withLocationsId(List<String> locationsId) {
            this.locationsId = locationsId;
            return this;
        }

        public Builder withLocationId(String locationId) {
            this.locationsId.add(locationId);
            return this;
        }

        public Builder withCityId(String cityId) {
            this.cityId = cityId;
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

        public Builder withDetails(HashMap<Language, UpdateEventDetailsResource> details) {
            this.details = details;
            return this;
        }

        public Builder withDetails(Language language, UpdateEventDetailsResource detailsResource) {
            details.put(language, detailsResource);
            return this;
        }

        public Builder withVersion(Long version) {
            this.version = version;
            return this;
        }

        public UpdateEventResource build() {
            return new UpdateEventResource(this);
        }

    }


}

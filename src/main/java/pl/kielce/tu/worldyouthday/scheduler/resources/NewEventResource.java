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
public class NewEventResource implements Serializable {

    private final List<String> locationsId;
    private final String cityId;
    private final Date startDate;
    private final Date endDate;
    private final HashMap<Language, NewEventDetailsResource> details;

    @JsonCreator
    public NewEventResource(
            @JsonProperty("locationsId") List<String> locationsId,
            @JsonProperty("cityId") String cityId,
            @JsonProperty("startDate") Date startDate,
            @JsonProperty("endDate") Date endDate,
            @JsonProperty("details") HashMap<Language, NewEventDetailsResource> details) {
        this.locationsId = locationsId;
        this.cityId = cityId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.details = details;
    }

    public NewEventResource(Builder builder) {
        this.locationsId = builder.locationsId;
        this.cityId = builder.cityId;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.details = builder.details;
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

    public HashMap<Language, NewEventDetailsResource> getDetails() {
        return details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewEventResource that = (NewEventResource) o;

        if (locationsId != null ? !locationsId.equals(that.locationsId) : that.locationsId != null) return false;
        if (cityId != null ? !cityId.equals(that.cityId) : that.cityId != null) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        return details != null ? details.equals(that.details) : that.details == null;

    }

    @Override
    public int hashCode() {
        int result = locationsId != null ? locationsId.hashCode() : 0;
        result = 31 * result + (cityId != null ? cityId.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (details != null ? details.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NewEventResource{" +
                "locationsId=" + locationsId +
                ", cityId='" + cityId + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", details=" + details +
                '}';
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        private List<String> locationsId = new ArrayList<>();
        private String cityId;
        private Date startDate;
        private Date endDate;
        private HashMap<Language, NewEventDetailsResource> details = new HashMap<>();

        public Builder withLocationsId(List<String> locationId) {
            this.locationsId = locationId;
            return this;
        }

        public Builder withLocationsId(String locationId) {
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

        public Builder withDetails(HashMap<Language, NewEventDetailsResource> details) {
            this.details = details;
            return this;
        }

        public Builder withDetails(Language language, NewEventDetailsResource detailsResource) {
            details.put(language, detailsResource);
            return this;
        }

        public NewEventResource build() {
            return new NewEventResource(this);
        }

    }


}

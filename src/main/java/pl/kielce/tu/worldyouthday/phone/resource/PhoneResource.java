package pl.kielce.tu.worldyouthday.phone.resource;

import pl.kielce.tu.worldyouthday.language.Language;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlRootElement
public class PhoneResource implements Serializable{

    private Long version;
    private String id;
    private String number;
    private Map<Language, PhoneDetailsResource> details;
    private List<String> cityIds;

    private PhoneResource(Builder builder) {
        version = builder.version;
        id = builder.id;
        number = builder.number;
        details =  builder.details;
        cityIds = builder.cityIds;
    }

    public Long getVersion() {
        return version;
    }

    public String getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public Map<Language, PhoneDetailsResource> getDetails() {
        return details;
    }

    public List<String> getCityIds() {
        return cityIds;
    }

    public static Builder newBuilder() { return new Builder(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhoneResource that = (PhoneResource) o;

        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        if (details != null ? !details.equals(that.details) : that.details != null) return false;
        return cityIds != null ? cityIds.equals(that.cityIds) : that.cityIds == null;

    }

    @Override
    public int hashCode() {
        int result = version != null ? version.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (details != null ? details.hashCode() : 0);
        result = 31 * result + (cityIds != null ? cityIds.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PhoneResource{" +
                "version=" + version +
                ", id='" + id + '\'' +
                ", number='" + number + '\'' +
                ", details=" + details +
                ", cityIds=" + cityIds +
                '}';
    }

    public static class Builder {
        private Long version;
        private String id;
        private String number;
        private Map<Language, PhoneDetailsResource> details = new HashMap<>();
        private List<String> cityIds = new ArrayList<>();

        public Builder withVersion(Long version) {
            this.version = version;
            return this;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withNumber(String number) {
            this.number = number;
            return this;
        }

        public Builder withDetail(Language language, PhoneDetailsResource detailsResource) {
            this.details.put(language, detailsResource);
            return this;
        }

        public Builder withDetails(Map<Language, PhoneDetailsResource> details) {
            this.details = details;
            return this;
        }

        public Builder withCity(String city) {
            this.cityIds.add(city);
            return this;
        }

        public Builder withCities(List<String> cityIds) {
            this.cityIds = cityIds;
            return this;
        }

        public PhoneResource build() {
            return new PhoneResource(this);
        }
    }
}

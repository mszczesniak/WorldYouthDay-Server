package pl.kielce.tu.worldyouthday.phone.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.kielce.tu.worldyouthday.language.Language;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlRootElement
public class NewPhoneResource {

    private String number;
    private Map<Language, NewPhoneDetailsResource> details;
    private List<String> cityIds;

    @JsonCreator
    public NewPhoneResource(
            @JsonProperty("number") String number,
            @JsonProperty("details") Map<Language, NewPhoneDetailsResource> details,
            @JsonProperty("cityIds") List<String> cityIds) {
        this.number = number;
        this.details = details;
        this.cityIds = cityIds;
    }

    public NewPhoneResource(NewPhoneResource.Builder builder) {
        number = builder.number;
        details = builder.details;
        cityIds = builder.cityIds;
    }

    public String getNumber() {
        return number;
    }

    public Map<Language, NewPhoneDetailsResource> getDetails() {
        return details;
    }

    public List<String> getCityIds() {
        return cityIds;
    }

    public static NewPhoneResource.Builder newBuilder() { return new NewPhoneResource.Builder(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewPhoneResource that = (NewPhoneResource) o;

        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        if (details != null ? !details.equals(that.details) : that.details != null) return false;
        return cityIds != null ? cityIds.equals(that.cityIds) : that.cityIds == null;

    }

    @Override
    public int hashCode() {
        int result = number != null ? number.hashCode() : 0;
        result = 31 * result + (details != null ? details.hashCode() : 0);
        result = 31 * result + (cityIds != null ? cityIds.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NewPhoneResource{" +
                "number='" + number + '\'' +
                ", details=" + details +
                ", cityIds=" + cityIds +
                '}';
    }

    public static class Builder {
        private String number;
        private Map<Language, NewPhoneDetailsResource> details = new HashMap<>();
        private List<String> cityIds = new ArrayList<>();

        public NewPhoneResource.Builder withNumber(String number) {
            this.number = number;
            return this;
        }

        public Builder withDetail(Language language, NewPhoneDetailsResource detailsResource) {
            this.details.put(language, detailsResource);
            return this;
        }

        public Builder withDetails(Map<Language, NewPhoneDetailsResource> details) {
            this.details = details;
            return this;
        }

        public NewPhoneResource.Builder withCity(String cityId) {
            this.cityIds.add(cityId);
            return this;
        }

        public NewPhoneResource.Builder withCities(List<String> cityIds) {
            this.cityIds = cityIds;
            return this;
        }

        public NewPhoneResource build() {
            return new NewPhoneResource(this);
        }
    }
}
